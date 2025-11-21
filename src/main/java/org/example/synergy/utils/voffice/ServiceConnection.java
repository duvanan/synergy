//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example.synergy.utils.voffice;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
@Slf4j
public class ServiceConnection {
    private final String KEY = "VIAESKEYSPACE";

    public ServiceConnection() {
    }

    public String getCodeRequest(String dataRequest) {
        String strResult = FunctionCommon.getItemInJson("result", dataRequest).toString();
        String errorCode = "205";
        if (strResult != null) {
            String mess = FunctionCommon.getItemInJson("mess", strResult).toString();
            if (mess != null) {
                errorCode = FunctionCommon.getItemInJson("errorCode", mess).toString();
            }
        }

        return errorCode;
    }

    public String sendPostRequest(String sessionUrl, String function, Object paramsVariable, String inputAesKey, String inputPublicKey, String sessionId) {
        String strResult = "";
        HttpURLConnection conn = null;

        try {
            String input = "";
            if (paramsVariable instanceof Map) {
                Map<String, Object> map = (Map)paramsVariable;
                input = FunctionCommon.convertValueToJson(map);
            } else if (paramsVariable instanceof String || paramsVariable instanceof JSONObject) {
                input = (String)paramsVariable;
            }

            Map<String, Object> params = new LinkedHashMap();
            String strDataSend = "";
            if (inputAesKey != null) {
                AES aesEncode = AES.getInstance();
                String[] arrKey = inputAesKey.split("VIAESKEYSPACE");
                if (arrKey != null && arrKey.length > 1) {
                    strDataSend = aesEncode.encrypt(input, arrKey[0], arrKey[1]);
                }

                params.put("isSecurity", "1");
                params.put("aesKey", RSA.getAesKey(inputAesKey, inputPublicKey));
                params.put("publicRsaKey", inputPublicKey);
            } else {
                strDataSend = input;
            }

            params.put("data", strDataSend);
            StringBuilder postData = new StringBuilder();
            URL url = new URL(sessionUrl + function.replace(".", "/"));
            Iterator i$ = params.entrySet().iterator();

            while(i$.hasNext()) {
                Map.Entry<String, Object> param = (Map.Entry)i$.next();
                if (postData.length() != 0) {
                    postData.append('&');
                }

                postData.append(URLEncoder.encode((String)param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }

            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            DisableSSL.disableSslVerification();
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setRequestProperty("session_id", sessionId);
            conn.setRequestProperty("Cookie", "JSESSIONID=" + sessionId);
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);
            conn.setConnectTimeout(5000);
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();

                String inputLine;
                while((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                strResult = response.toString();
            } else {
                strResult = "{\"result\":{\"mess\":{\"errorCode\":201,\"message\":\"Loi lay du lieu\"}}}";
            }
        } catch (SocketTimeoutException var24) {
            strResult = "{\"result\":{\"mess\":{\"errorCode\":202,\"message\":\"Loi timeout service\"}}}";
            Logger.getLogger(ServiceConnection.class.getName()).log(Level.SEVERE, strResult, var24);
        } catch (IOException var25) {
            strResult = "{\"result\":{\"mess\":{\"errorCode\":203,\"message\":\"Loi khong ket noi duoc service\"}}}";
            Logger.getLogger(ServiceConnection.class.getName()).log(Level.SEVERE, strResult, var25);
        } catch (Exception var26) {
            strResult = "{\"result\":{\"mess\":{\"errorCode\":203,\"message\":\"Loi khong ket noi duoc service\"}}}";
            Logger.getLogger(ServiceConnection.class.getName()).log(Level.SEVERE, strResult, var26);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

        }

        return strResult;
    }

    public int sendDownloadRequest(String sessionUrl, String function, Map<String, Object> args, String inputAesKey, String inputPublicKey, String sessionId, String path) {
        HttpURLConnection httpConn = null;

        try {
            Map<String, Object> params = new LinkedHashMap();
            String strDataSend = "";
            if (inputAesKey != null) {
                AES aesEncode = AES.getInstance();
                String[] arrKey = inputAesKey.split("VIAESKEYSPACE");
                if (arrKey != null && arrKey.length > 1) {
                    strDataSend = aesEncode.encrypt(FunctionCommon.convertValueToJson(args), arrKey[0], arrKey[1]);
                }

                params.put("isSecurity", "1");
                params.put("aesKey", RSA.getAesKey(inputAesKey, inputPublicKey));
                params.put("publicRsaKey", inputPublicKey);
            } else {
                strDataSend = FunctionCommon.convertValueToJson(args);
            }

            params.put("data", strDataSend);
            StringBuilder postData = new StringBuilder();
            URL url = new URL(sessionUrl + function.replace(".", "/"));
            Iterator i$ = params.entrySet().iterator();

            while(i$.hasNext()) {
                Map.Entry<String, Object> param = (Map.Entry)i$.next();
                if (postData.length() != 0) {
                    postData.append('&');
                }

                postData.append(URLEncoder.encode((String)param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }

            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            httpConn = (HttpURLConnection)url.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            httpConn.setRequestProperty("session_id", sessionId);
            httpConn.setRequestProperty("Cookie", "JSESSIONID=" + sessionId);
            httpConn.setDoOutput(true);
            httpConn.getOutputStream().write(postDataBytes);
            httpConn.setConnectTimeout(5000);
            int responseCode = httpConn.getResponseCode();
            if (responseCode != 200) {
                return -1;
            } else {
                String responsePermission = httpConn.getHeaderField("filePermission");
                if ("0".equals(responsePermission)) {
                    byte var31 = 0;
                    return var31;
                } else {
                    File file = new File(path);

                    try (InputStream inputStream = httpConn.getInputStream();
                         FileOutputStream outputStream = new FileOutputStream(file)) {

                        byte[] buffer = new byte[8192];
                        int bytesRead;

                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }

                        return 1;

                    } catch (IOException e) {
                        log.error("Error writing to file: " + path, e);
                        e.printStackTrace();
                        return 0; // hoặc xử lý lỗi theo cách khác
                    }
                }
            }
        } catch (Exception var25) {
            Logger.getLogger(ServiceConnection.class.getName()).log(Level.SEVERE, var25.getMessage(), var25);
            return -1;
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }

        }
    }

    public byte[] sendDownloadRequest(String sessionUrl, String function, Map<String, Object> args, String inputAesKey, String inputPublicKey, String sessionId) {
        HttpURLConnection httpConn = null;

        InputStream result;
        try {
            Map<String, Object> params = new LinkedHashMap();
            String strDataSend = "";
            if (inputAesKey != null) {
                AES aesEncode = AES.getInstance();
                String[] arrKey = inputAesKey.split("VIAESKEYSPACE");
                if (arrKey != null && arrKey.length > 1) {
                    strDataSend = aesEncode.encrypt(FunctionCommon.convertValueToJson(args), arrKey[0], arrKey[1]);
                }

                params.put("isSecurity", "1");
                params.put("aesKey", RSA.getAesKey(inputAesKey, inputPublicKey));
                params.put("publicRsaKey", inputPublicKey);
            } else {
                strDataSend = FunctionCommon.convertValueToJson(args);
            }

            params.put("deviceName", "Trinh ky tu dong");
            params.put("data", strDataSend);
            StringBuilder postData = new StringBuilder();
            URL url = new URL(sessionUrl + function.replace(".", "/"));
            Iterator i$ = params.entrySet().iterator();

            while(i$.hasNext()) {
                Map.Entry<String, Object> param = (Map.Entry)i$.next();
                if (postData.length() != 0) {
                    postData.append('&');
                }

                postData.append(URLEncoder.encode((String)param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }

            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            httpConn = (HttpURLConnection)url.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            httpConn.setRequestProperty("session_id", sessionId);
            httpConn.setRequestProperty("Cookie", "JSESSIONID=" + sessionId);
            httpConn.setDoOutput(true);
            httpConn.getOutputStream().write(postDataBytes);
            httpConn.setConnectTimeout(5000);
            int responseCode = httpConn.getResponseCode();
            if (responseCode != 200) {
                return null;
            }

            String responsePermission = httpConn.getHeaderField("filePermission");
            if (!"0".equals(responsePermission)) {
                result = httpConn.getInputStream();
                byte[] bytes = IOUtils.toByteArray(result);
                return bytes;
            }

            result = null;
        } catch (Exception var21) {
            Logger.getLogger(ServiceConnection.class.getName()).log(Level.SEVERE, (String)null, var21);
            return null;
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }

        }

        return null;
    }
}
