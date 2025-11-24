//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example.synergy.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.example.synergy.utils.voffice.AES;
import org.example.synergy.utils.voffice.FunctionCommon;
import org.example.synergy.utils.voffice.User;
import org.example.synergy.utils.voffice.VhrOrgEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectVOfficeService {
    private static ServiceConnection connect = new ServiceConnection();
    private static final String KEY = "VIAESKEYSPACE";
    private static final long TIMEOUT = 1800000L;
    private String rsaPublicKey = "";
    private String aesKey = "";
    private String sessionId = "";

    @Value("${voffice.url}")
    private String url;
    private String username = null;
    private String pass = null;
    private String jwt = null;
    private static final Map<String, User> MAP_LOGIN = new HashMap();
    private User userInformation;

    public ConnectVOfficeService(String url, String username, String pass) {
        if (url != null && !url.isEmpty()) {
            this.url = url;
        }

        this.username = username;
        this.pass = pass;
        this.login();
    }

    public ConnectVOfficeService(String url, String jwt) {
        if (url != null && !url.isEmpty()) {
            this.url = url;
        }

        this.jwt = jwt;
        this.login();
    }

    public String getDataFromService(String api, Object params) {
        if (api != null && !api.isEmpty()) {
            String result = this.login();
            if (result != null && !result.isEmpty()) {
                return this.serveProcessing(api, params);
            }
        }

        return null;
    }

    public byte[] getFileFromService(String api, Map<String, Object> params) {
        if (api != null && !api.isEmpty()) {
            String result = this.login();
            if (result != null && !result.isEmpty()) {
                return this.downloadFile(api, params);
            }
        }

        return null;
    }

    public String uploadFileToService(MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String result = this.login();
            if (result != null && !result.isEmpty()) {
                return this.uploadFile(multipartFile);
            }
        }

        return null;
    }

    public String getUserInfor(String keyword) {
        try {
            if (keyword != null && !keyword.isEmpty()) {
                String result = this.login();
                if (result != null && !result.isEmpty()) {
                    Map<String, Object> params = new LinkedHashMap();
                    params.put("keyword", keyword);
                    params.put("pageSize", "10");
                    params.put("startRecord", "0");
                    params.put("searchType", "1");
                    result = this.serveProcessing("staffAction.getListUser", params);
                    if (result != null && !result.isEmpty()) {
                        JSONArray jsonArray = new JSONArray(result);
                        if (jsonArray.length() > 0) {
                            JSONObject jsonObject = null;

                            String orgName;
                            for(int i = 0; i < jsonArray.length(); ++i) {
                                JSONObject jsonObjectNew = jsonArray.getJSONObject(i);
                                if (jsonObjectNew != null && jsonObjectNew.has("email")) {
                                    orgName = jsonObjectNew.getString("email");
                                    if (keyword.equalsIgnoreCase(orgName)) {
                                        jsonObject = jsonObjectNew;
                                        break;
                                    }
                                }
                            }

                            if (jsonObject == null) {
                                return null;
                            }

                            String id = null;
                            String code = null;
                            orgName = null;
                            if (jsonObject.has("employeeId")) {
                                id = jsonObject.getString("employeeId");
                            }

                            if (jsonObject.has("position")) {
                                orgName = jsonObject.getString("position");
                            }

                            if (jsonObject.has("orgName") && orgName != null && !orgName.isEmpty()) {
                                orgName = orgName + " / " + jsonObject.getString("orgName");
                            }

                            if (orgName != null && !orgName.isEmpty()) {
                                jsonObject.put("orgName", orgName);
                                jsonObject.remove("position");
                            }

                            if (jsonObject.has("employeeCode")) {
                                code = jsonObject.getString("employeeCode");
                            }

                            if (id != null && !id.isEmpty() && code != null && !code.isEmpty()) {
                                params = new LinkedHashMap();
                                params.put("isRequestToSignText", "1");
                                params.put("signStaffIdV2", id);
                                params.put("signStaffCode", code);
                                params.put("textCreatedDate", FunctionCommon.dateToString(new Date()));
                                result = this.serveProcessing("imageSignAction.search", params);
                                if (result != null && !result.isEmpty()) {
                                    jsonArray = new JSONArray(result);
                                    if (jsonArray.length() > 0) {
                                        JSONObject imageObject = null;

                                        for(int i = 0; i < jsonArray.length(); ++i) {
                                            imageObject = jsonArray.getJSONObject(0);
                                            if (imageObject.has("staffImageSignId")) {
                                                jsonObject.put("staffImageSignId", imageObject.getString("staffImageSignId"));
                                            }

                                            if (imageObject.has("type") && "1".equals(imageObject.getString("type"))) {
                                                break;
                                            }
                                        }
                                    }
                                }
                            }

                            return jsonObject.toString();
                        }
                    }
                }
            }
        } catch (JSONException var11) {
            Logger.getLogger(ConnectVOfficeService.class.getName()).log(Level.SEVERE, (String)null, var11);
        }

        return null;
    }

    private String login() {
        boolean loginAgain = true;
        User user = null;
        long currentTime = (new Date()).getTime();
        if (this.jwt == null || this.jwt.isEmpty()) {
            user = (User)MAP_LOGIN.get(this.username);
            if (user != null) {
                long loginTime = user.getLoginTime();
                if (currentTime - loginTime <= 1800000L) {
                    this.sessionId = user.getSessionId();
                    this.rsaPublicKey = user.getRsaPublicKey();
                    this.aesKey = user.getAesKey();
                    loginAgain = false;
                }
            }
        }

        if (loginAgain) {
            String dataRequest = connect.sendPostRequest(this.url, "Authenticate.getRsaKeyPublic", (Object)null, (String)null, (String)null, this.sessionId);
            String codeError = connect.getCodeRequest(dataRequest);
            if ("200".equals(codeError)) {
                try {
                    this.rsaPublicKey = FunctionCommon.getDataByKeyJson(dataRequest, "result.data.strPublicKey").toString();
                    this.aesKey = AES.createAesKey();
                    Map<String, Object> params = new LinkedHashMap();
                    String strFunction = "Authenticate.login";
                    if (this.jwt != null && !this.jwt.isEmpty()) {
                        params.put("ticket", this.jwt);
                        params.put("deviceName", "Trinh ky tu dong");
                        strFunction = "Authenticate.LoginJWT";
                    } else {
                        params.put("loginName", this.username);
                        params.put("passWord", FunctionCommon.encrypt(this.pass));
                        params.put("vof2Key", this.pass);
                        params.put("deviceName", "Trinh ky tu dong");
                    }

                    dataRequest = connect.sendPostRequest(this.url, strFunction, params, this.aesKey, this.rsaPublicKey, this.sessionId);
                    String codeErrLogin = connect.getCodeRequest(dataRequest);
                    if ("200".equals(codeErrLogin)) {
                        String item = FunctionCommon.getItemInJson("result", dataRequest).toString();
                        String dataEncrypt = FunctionCommon.getItemInJson("data", item).toString();
                        AES aes = AES.getInstance();
                        String dataDecrypt = aes.decrypt(dataEncrypt, this.aesKey.split("VIAESKEYSPACE")[0], this.aesKey.split("VIAESKEYSPACE")[1]);
                        if (dataDecrypt != null) {
                            this.sessionId = FunctionCommon.getDataByKeyJson(dataDecrypt, "strSessionId").toString();
                            user = new User(this.username, this.pass, this.rsaPublicKey, this.aesKey, this.sessionId, currentTime);
                            MAP_LOGIN.put(this.username, user);
                            Gson gson = (new GsonBuilder()).setDateFormat("dd/MM/yyyy hh:mm:ss").create();
                            this.userInformation = (User)gson.fromJson(dataDecrypt, User.class);
                            if (this.userInformation != null) {
                                ArrayList ids;
                                Iterator i$;
                                VhrOrgEntity obj;
                                if (this.userInformation.getListAssistantVhrOrg() != null) {
                                    ids = new ArrayList();
                                    i$ = this.userInformation.getListAssistantVhrOrg().iterator();

                                    while(i$.hasNext()) {
                                        obj = (VhrOrgEntity)i$.next();
                                        if (obj.getSysOrganizationId() != null) {
                                            ids.add(obj.getSysOrganizationId());
                                        }
                                    }

                                    this.userInformation.setListAssistantVhrOrgId(ids);
                                }

                                if (this.userInformation.getListManagementVhrOrg() != null) {
                                    ids = new ArrayList();
                                    i$ = this.userInformation.getListManagementVhrOrg().iterator();

                                    while(i$.hasNext()) {
                                        obj = (VhrOrgEntity)i$.next();
                                        if (obj.getSysOrganizationId() != null) {
                                            ids.add(obj.getSysOrganizationId());
                                        }
                                    }

                                    this.userInformation.setListManagementVhrOrgId(ids);
                                }
                            }

                            return this.sessionId;
                        }
                    }
                } catch (Exception var18) {
                    Logger.getLogger(ConnectVOfficeService.class.getName()).log(Level.SEVERE, (String)null, var18);
                }
            }
        }

        return user != null && user.getSessionId() != null ? user.getSessionId() : null;
    }

    public String loginJWT(String ticket) {
        String dataRequest = connect.sendPostRequest(this.url, "Authenticate.getRsaKeyPublic", (Object)null, (String)null, (String)null, this.sessionId);
        String codeError = connect.getCodeRequest(dataRequest);
        if ("200".equals(codeError)) {
            try {
                this.rsaPublicKey = FunctionCommon.getDataByKeyJson(dataRequest, "result.data.strPublicKey").toString();
                this.aesKey = AES.createAesKey();
                Map<String, Object> params = new LinkedHashMap();
                params.put("ticket", ticket);
                params.put("deviceName", "deviceName");
                String strFunction = "Authenticate.LoginJWT";
                dataRequest = connect.sendPostRequest(this.url, strFunction, params, this.aesKey, this.rsaPublicKey, this.sessionId);
                String codeErrLogin = connect.getCodeRequest(dataRequest);
                if ("200".equals(codeErrLogin)) {
                    String item = FunctionCommon.getItemInJson("result", dataRequest).toString();
                    String dataEncrypt = FunctionCommon.getItemInJson("data", item).toString();
                    AES aes = AES.getInstance();
                    String dataDecrypt = aes.decrypt(dataEncrypt, this.aesKey.split("VIAESKEYSPACE")[0], this.aesKey.split("VIAESKEYSPACE")[1]);
                    if (dataDecrypt != null) {
                        this.sessionId = FunctionCommon.getDataByKeyJson(dataDecrypt, "strSessionId").toString();
                        System.out.println("------- Login success ! -------");
                        Gson gson = (new GsonBuilder()).setDateFormat("dd/MM/yyyy hh:mm:ss").create();
                        this.userInformation = (User)gson.fromJson(dataDecrypt, User.class);
                        if (this.userInformation != null) {
                            ArrayList ids;
                            Iterator i$;
                            VhrOrgEntity obj;
                            if (this.userInformation.getListAssistantVhrOrg() != null) {
                                ids = new ArrayList();
                                i$ = this.userInformation.getListAssistantVhrOrg().iterator();

                                while(i$.hasNext()) {
                                    obj = (VhrOrgEntity)i$.next();
                                    if (obj.getSysOrganizationId() != null) {
                                        ids.add(obj.getSysOrganizationId());
                                    }
                                }

                                this.userInformation.setListAssistantVhrOrgId(ids);
                            }

                            if (this.userInformation.getListManagementVhrOrg() != null) {
                                ids = new ArrayList();
                                i$ = this.userInformation.getListManagementVhrOrg().iterator();

                                while(i$.hasNext()) {
                                    obj = (VhrOrgEntity)i$.next();
                                    if (obj.getSysOrganizationId() != null) {
                                        ids.add(obj.getSysOrganizationId());
                                    }
                                }

                                this.userInformation.setListManagementVhrOrgId(ids);
                            }
                        }

                        return this.sessionId;
                    }
                }
            } catch (Exception var15) {
                Logger.getLogger(ConnectVOfficeService.class.getName()).log(Level.SEVERE, (String)null, var15);
            }
        }

        return null;
    }

    private String serveProcessing(String functionName, Object params) {
        String result = null;

        try {
            String dataRequest = connect.sendPostRequest(this.url, functionName, params, this.aesKey, this.rsaPublicKey, this.sessionId);
            String error = connect.getCodeRequest(dataRequest);
            int errorCode = Integer.parseInt(error);
            if (errorCode == 200) {
                AES aes = AES.getInstance();
                String response = FunctionCommon.getItemInJson("result", dataRequest).toString();
                String dataEncrypt = FunctionCommon.getItemInJson("data", response).toString();
                result = aes.decrypt(dataEncrypt, this.aesKey.split("VIAESKEYSPACE")[0], this.aesKey.split("VIAESKEYSPACE")[1]);
            }
        } catch (NumberFormatException var10) {
            Logger.getLogger(ConnectVOfficeService.class.getName()).log(Level.SEVERE, (String)null, var10);
        }

        return result;
    }

    private byte[] downloadFile(String functionName, Map<String, Object> params) {
        if (connect != null) {
            try {
                return connect.sendDownloadRequest(this.url, functionName, params, this.aesKey, this.rsaPublicKey, this.sessionId);
            } catch (Exception var4) {
                Logger.getLogger(ConnectVOfficeService.class.getName()).log(Level.SEVERE, (String)null, var4);
            }
        }

        return null;
    }

//    private String uploadFile(String filePathUpload) {
//        try {
//            File file = new File(filePathUpload);
//            if (file.exists()) {
//                String API_URL = this.url + "Files/UploadTmpFile";
//                DefaultClientConfig defaultClientConfig = new DefaultClientConfig();
//                Client client = Client.create(defaultClientConfig);
//                WebResource resource = client.resource(API_URL);
//                WebResource.Builder builder = resource.getRequestBuilder();
//                builder.header("content-type", "multipart/form-data");
//                String strCookie = connect != null && this.sessionId != null ? this.sessionId : "";
//                builder.header("session_id", strCookie);
//                builder.header("Cookie", "JSESSIONID=" + strCookie);
//                FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("file", file, MediaType.APPLICATION_OCTET_STREAM_TYPE);
//                fileDataBodyPart.setContentDisposition(((FormDataContentDisposition.FormDataContentDispositionBuilder)FormDataContentDisposition.name("file").fileName(file.getName())).build());
//                JSONObject jsonObj = new JSONObject();
//                jsonObj.put("type", "5");
//                MultiPart multiPart = (new FormDataMultiPart()).field("data", jsonObj.toString()).bodyPart(fileDataBodyPart);
//                multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
//                ClientResponse response = (ClientResponse)((WebResource.Builder)builder.type(MediaType.MULTIPART_FORM_DATA_TYPE)).post(ClientResponse.class, multiPart);
//                if (response != null && response.getStatus() == 200) {
//                    String result = (String)response.getEntity(String.class);
//                    return (new JsonParser()).parse(result).getAsJsonObject().getAsJsonObject("result").getAsJsonObject("data").get("attachment").getAsString();
//                }
//            }
//        } catch (UniformInterfaceException | IllegalArgumentException | JSONException | ClientHandlerException var14) {
//            Logger.getLogger(ConnectVOfficeService.class.getName()).log(Level.SEVERE, (String)null, var14);
//        }
//
//        return null;
//    }
    private File convertToFile(MultipartFile multipartFile) throws IOException {
        File convFile = File.createTempFile("upload_", "_" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(convFile);
        convFile.deleteOnExit(); // Tự xóa khi JVM thoát
        return convFile;
    }

    private String uploadFile(MultipartFile multipartFile) throws IOException {
        File file = convertToFile(multipartFile);
//        if (!file.exists()) return null;

        try {
            String API_URL = this.url + "Files/UploadTmpFile";
            String boundary = "----Java11FormBoundary" + System.currentTimeMillis();
            String lineSeparator = "\r\n";

            // Build multipart body
            StringBuilder builder = new StringBuilder();
            builder.append("--").append(boundary).append(lineSeparator);
            builder.append("Content-Disposition: form-data; name=\"data\"").append(lineSeparator).append(lineSeparator);
            builder.append("{\"type\":\"5\"}").append(lineSeparator);

            builder.append("--").append(boundary).append(lineSeparator);
            builder.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(file.getName()).append("\"").append(lineSeparator);
            builder.append("Content-Type: application/octet-stream").append(lineSeparator).append(lineSeparator);

            byte[] fileBytes = Files.readAllBytes(file.toPath());
            byte[] preamble = builder.toString().getBytes(StandardCharsets.UTF_8);
            byte[] epilogue = (lineSeparator + "--" + boundary + "--").getBytes(StandardCharsets.UTF_8);

            // Combine into full request body
            byte[] requestBody = ByteBuffer
                    .allocate(preamble.length + fileBytes.length + epilogue.length)
                    .put(preamble)
                    .put(fileBytes)
                    .put(epilogue)
                    .array();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .header("session_id", sessionId != null ? sessionId : "")
                    .header("Cookie", "JSESSIONID=" + (sessionId != null ? sessionId : ""))
                    .POST(HttpRequest.BodyPublishers.ofByteArray(requestBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                return json.getAsJsonObject("result")
                        .getAsJsonObject("data")
                        .get("attachment")
                        .getAsString();
            }

        } catch (Exception e) {
            Logger.getLogger(ConnectVOfficeService.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }


    public User getUserInformation() {
        return this.userInformation;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setUserInformation(User userInformation) {
        this.userInformation = userInformation;
    }

    public List<Long> getEmployeeByOrg(List<Long> orgIds, boolean getParent) {
        Map<String, Object> params = new LinkedHashMap();
        params.put("orgId", orgIds);
        params.put("roleId", new ArrayList(Arrays.asList("TTDV", "LDDV")));
        if (getParent) {
            params.put("parentId", 1);
        }

        String result = this.serveProcessing("staffAction.getEmployeeByOrg", params);
        if (result != null && !result.isEmpty()) {
            Gson gson = new Gson();
            Type longType = (new TypeToken<ArrayList<Long>>() {
            }).getType();
            return (List)gson.fromJson(result, longType);
        } else {
            return null;
        }
    }
}
