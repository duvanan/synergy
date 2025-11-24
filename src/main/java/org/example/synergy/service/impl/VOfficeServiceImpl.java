package org.example.synergy.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.example.synergy.contants.DocumentSignEnum;
import org.example.synergy.contants.VofficeStatus;
import org.example.synergy.dto.EmployeeDTO;
import org.example.synergy.dto.VofficeFileDto;
import org.example.synergy.dto.request.VOfficeFileRequest;
import org.example.synergy.dto.request.voffice.*;
import org.example.synergy.dto.response.*;
import org.example.synergy.entity.DocumentSignEntity;
import org.example.synergy.entity.ProgramSignatureDetail;
import org.example.synergy.entity.SignerListEntity;
import org.example.synergy.repository.DocumentSignRepository;
import org.example.synergy.repository.ProgramSignatureDetailRepository;
import org.example.synergy.repository.SignerListRepository;
import org.example.synergy.service.ConnectVOfficeService;
import org.example.synergy.service.MinioService;
import org.example.synergy.service.VOfficeService;
import org.example.synergy.utils.voffice.AESUtil;
import org.example.synergy.utils.voffice.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor

public class VOfficeServiceImpl implements VOfficeService {
    @Value("${voffice.url}")
    private String URL;
//    private static final String USER = "tuantm30";
//    private static final String PASS = "222222a@";

    private static final ObjectMapper mapper = new ObjectMapper();

    private final ProgramSignatureDetailRepository programSignatureDetailRepository;
    private final SignerListRepository signerListRepository;
    private final DocumentSignRepository documentSignRepository;
    private final MinioService minioService;
//    private final StaffRepository staffRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Value("${minio.bucketName}")
    private String minioBucketName;

    private ConnectVOfficeService getConnectService(String userName, String password) throws Exception {
        return new ConnectVOfficeService(URL, userName, AESUtil.decryptCryptoJS(password));
    }

    @Override
    @Transactional
    public Long addText(AddTextRequest request) {

        ProgramSignatureDetail programSignatureDetail = new ProgramSignatureDetail();
//        programSignatureDetail.setProgramPlanVersionId(request.getProgramPlanVersionId());
        programSignatureDetail.setDocumentCode(request.getDocumentCode());
        programSignatureDetail.setDocumentName(request.getDocumentName());
        programSignatureDetail.setDocumentContent(request.getDocumentContent());
        programSignatureDetail.setAreaId(request.getAreaId());
        programSignatureDetail.setAreaName(request.getAreaName());
        programSignatureDetail.setTypeId(request.getTypeId());
        programSignatureDetail.setTypeName(request.getTypeName());
        programSignatureDetail.setStypeId(request.getStypeId());
        programSignatureDetail.setStypeName(request.getStypeName());
        programSignatureDetail.setPriorityId(request.getPriorityId());
        programSignatureDetail.setPriorityName(request.getPriorityName());
        programSignatureDetail.setReceivingPlace(request.getReceivingPlace());
        programSignatureDetail.setAutoPromulgateText(request.getAutoPromulgateText());
        programSignatureDetail.setStatus(VofficeStatus.DA_TAO_VAN_BAN_TRINH_KY.getValue());
        programSignatureDetailRepository.save(programSignatureDetail);

        List<SignerListEntity> signerListEntities = new ArrayList<>();
        request.getLstStaff().forEach(staff -> {
            SignerListEntity signer = new SignerListEntity();
            signer.setSignatureDetailId(programSignatureDetail.getId());
            signer.setStaffId(staff.getStaffId());
            signer.setStaffName(staff.getStaffName());
            signer.setEmail(staff.getEmail());
            signer.setNameImage(staff.getNameImage());
            signer.setSignLevel(staff.getSignLevel());
            signer.setShowImage(staff.getShowImage());
            signer.setOrganizationName(staff.getOrganizationName());
            signer.setOrganizationId(staff.getOrganizationId());
            signer.setSignImageId(staff.getSignImageId());
            signer.setPromulgatingSigner(staff.getPromulgatingSigner());
            signer.setParallelsignSignGroup(staff.getParallelSignGroup());
            if (staff.getFileImage() != null && !staff.getFileImage().isEmpty()) {
                String tmpName = UUID.randomUUID() + "." + staff.getFileImage().getOriginalFilename();
                String response = minioService.uploadFile(staff.getFileImage(), minioBucketName, tmpName);
                if ("Done".equals(response)) {
                    signer.setPathImage(tmpName);
                    signer.setBucketName(minioBucketName);
                }
            }
            signerListEntities.add(signer);
        });
        signerListRepository.saveAll(signerListEntities);

        List<DocumentSignEntity> documentSigns = new ArrayList<>();
        request.getLstFileSign().forEach(file -> {
            DocumentSignEntity sign = new DocumentSignEntity();
            String tmpName = UUID.randomUUID() + "." + file.getOriginalFilename();
            minioService.uploadFile(file, minioBucketName, tmpName);
            sign.setSignatureDetailId(programSignatureDetail.getId());
            sign.setName(file.getOriginalFilename());
            sign.setPath(tmpName);
            sign.setBucketName(minioBucketName);
            sign.setDocumentType(DocumentSignEnum.DOCUMENT_SIGN.getValue());
            documentSigns.add(sign);
        });
        if (request.getListFileSignOther() != null) {
            request.getListFileSignOther().forEach(file -> {
                DocumentSignEntity other = new DocumentSignEntity();
                String tmpNameOther = UUID.randomUUID() + "." + file.getOriginalFilename();
                minioService.uploadFile(file, minioBucketName, tmpNameOther);
                other.setSignatureDetailId(programSignatureDetail.getId());
                other.setName(file.getOriginalFilename());
                other.setPath(tmpNameOther);
                other.setBucketName(minioBucketName);
                other.setDocumentType(DocumentSignEnum.DOCUMENT_TAG.getValue());
                documentSigns.add(other);
            });
        }
        if (request.getLstFileEvidence() != null) {
            request.getLstFileEvidence().forEach(file -> {
                        DocumentSignEntity other = new DocumentSignEntity();
                        String tmpNameOther = UUID.randomUUID() + "." + file.getOriginalFilename();
                        minioService.uploadFile(file, minioBucketName, tmpNameOther);
                        other.setSignatureDetailId(programSignatureDetail.getId());
                        other.setName(file.getOriginalFilename());
                        other.setPath(tmpNameOther);
                        other.setBucketName(minioBucketName);
                        other.setDocumentType(DocumentSignEnum.DOCUMENT_EVIDENCE.getValue());
                        documentSigns.add(other);
                    }
            );
        }
        if (request.getLstFileDocument() != null) {
            request.getLstFileDocument().forEach(file -> {
                        DocumentSignEntity other = new DocumentSignEntity();
                        String tmpNameOther = UUID.randomUUID() + "." + file.getOriginalFilename();
                        minioService.uploadFile(file, minioBucketName, tmpNameOther);
                        other.setSignatureDetailId(programSignatureDetail.getId());
                        other.setName(file.getOriginalFilename());
                        other.setPath(tmpNameOther);
                        other.setBucketName(minioBucketName);
                        other.setDocumentType(DocumentSignEnum.DOCUMENT_DOC.getValue());
                        documentSigns.add(other);
                    }
            );
        }
        documentSignRepository.saveAll(documentSigns);

        // Gọi sau
        Long documentId = addTextProcess(request);

        if (documentId == null || documentId == 0) {
            // rollback thủ công (nếu không dùng @Transactional rollback toàn bộ)
            signerListRepository.deleteAll(signerListEntities);
            documentSignRepository.deleteAll(documentSigns);
            programSignatureDetailRepository.delete(programSignatureDetail);
            throw new IllegalArgumentException("Trình ký không thành công, vui lòng thử lại sau!");
        }

        //trình ký voffice
        String transCode = sendAndSign(documentId, request.getUserName(), request.getPassword());

        programSignatureDetail.setDocumentId(documentId);
        programSignatureDetail.setTransCode(transCode);
        programSignatureDetailRepository.save(programSignatureDetail);

        return documentId;
    }
    public Long addTextProcess(AddTextRequest request) {
        try {
            ConnectVOfficeService service = getConnectService(request.getUserName(), request.getPassword());

            JSONObject jsonText = new JSONObject();
            // Begin - Thong tin chinh cua van ban trinh ky
            jsonText.put("typeId", request.getTypeId()); // id hinh thuc van ban
            jsonText.put("sTypeId", request.getStypeId()); // id do mat van ban: 1-binh thuong, 2-mat
            jsonText.put("priorityId", request.getPriorityId()); // id do khan van ban: 1-binh thuong, 2-khan, 3-Hoa toc, 4-Thuong khan
            jsonText.put("officePublishedId", request.getPromulgatingSignerDepartmentId()); // id don vi ban hanh
            jsonText.put("areaId", request.getAreaId()); // id linh vuc
            jsonText.put("code", request.getDocumentCode()); // so ky hieu van ban
            jsonText.put("registerNumber", "001"); // so dang ky
            jsonText.put("title", request.getDocumentName()); // trich yeu noi dung van ban
            jsonText.put("description", request.getDocumentContent()); // noi dung van ban
            jsonText.put("autoPromulgateText", request.getAutoPromulgateText()); // ban hanh tu dong: 0-ko ban hanh tu dong, 1-ban hanh tu dong
            jsonText.put("isActive", "1"); // tham so mac dinh
            jsonText.put("receiverPlace", request.getReceivingPlace()); // nơi nhận
            // End - Thong tin chinh cua van ban trinh ky

            // Begin - Thong tin file ky chinh
            List<MultipartFile> lstFileSign = request.getLstFileSign();
            // Begin - Thông tin file đính kèm
            JSONArray jsonArrayFileSign = new JSONArray();

            if (lstFileSign != null && !lstFileSign.isEmpty()) {
                for (int i = 0; i < lstFileSign.size(); i++) {
                    MultipartFile file = lstFileSign.get(i);
                    String resultOther = service.uploadFileToService(file);

                    JSONObject jsonFileSignOther = new JSONObject();
                    jsonFileSignOther.put("name", file.getOriginalFilename());
                    jsonFileSignOther.put("filePath", resultOther);
                    jsonFileSignOther.put("fileOrder", String.valueOf(i));

                    jsonArrayFileSign.put(jsonFileSignOther);
                }
            }
            jsonText.put("lstFileSign", jsonArrayFileSign);
            // End - Thong tin file ky chinh

            List<MultipartFile> listFileSignOther = request.getListFileSignOther();
            // Begin - Thông tin file đính kèm
            JSONArray jsonArrayFileSignOther = new JSONArray();

            if (listFileSignOther != null && !listFileSignOther.isEmpty()) {
                for (int i = 0; i < listFileSignOther.size(); i++) {
                    MultipartFile file = listFileSignOther.get(i);
                    String resultOther = service.uploadFileToService(file);

                    JSONObject jsonFileSignOther = new JSONObject();
                    jsonFileSignOther.put("name", file.getOriginalFilename());
                    jsonFileSignOther.put("filePath", resultOther);
                    jsonFileSignOther.put("fileOrder", String.valueOf(i));

                    jsonArrayFileSignOther.put(jsonFileSignOther);
                }
            }

            jsonText.put("listFileSignOther", jsonArrayFileSignOther);
            // End - Thong tin file dinh kem

            // Begin - Thông tin file sở cứ
            List<MultipartFile> listFileEvidence = request.getLstFileEvidence();

            JSONArray jsonArrayFileEvidence = new JSONArray();

            if (listFileEvidence != null && !listFileEvidence.isEmpty()) {
                for (int i = 0; i < listFileEvidence.size(); i++) {
                    MultipartFile file = listFileEvidence.get(i);
                    String resultOther = service.uploadFileToService(file);
                    JSONObject jsonFileEvidence = new JSONObject();
                    jsonFileEvidence.put("name", file.getOriginalFilename());
                    jsonFileEvidence.put("filePath", resultOther);
                    jsonFileEvidence.put("fileOrder", String.valueOf(i));

                    jsonArrayFileEvidence.put(jsonFileEvidence);
                }
            }
            jsonText.put("listBaseFile", jsonArrayFileEvidence);
            // End - Thông tin file sở cứ

            // Begin - Thông tin file sở cứ
            List<MultipartFile> listFileDocument = request.getLstFileDocument();

            JSONArray jsonArrayFileDocument = new JSONArray();

            if (listFileDocument != null && !listFileDocument.isEmpty()) {
                for (int i = 0; i < listFileDocument.size(); i++) {
                    MultipartFile file = listFileDocument.get(i);
                    String resultOther = service.uploadFileToService(file);
                    JSONObject jsonFileDocument = new JSONObject();
                    jsonFileDocument.put("name", file.getOriginalFilename());
                    jsonFileDocument.put("filePath", resultOther);
                    jsonFileDocument.put("fileOrder", String.valueOf(i));

                    jsonArrayFileDocument.put(jsonFileDocument);
                }
            }
            jsonText.put("listAttachTemplate", jsonArrayFileDocument);
            // End - Thông tin file sở cứ

            List<StaffDTO> lstStaff = request.getLstStaff();
            // Begin - Danh sách người ký
            JSONArray jsonStaffs = new JSONArray();

            if (lstStaff != null && !lstStaff.isEmpty()) {
                for (StaffDTO staff : lstStaff) {
                    JSONObject jsonStaff = new JSONObject();

                    long employeeId = 0;
//                    Staff staffs = staffRepository.findById(staff.getStaffId()).orElseThrow(() -> new BusinessException("Nhân viên có ID " + staff.getStaffId() + " không tồn tại!"));
                    EmployeeRequest employeeRequest = new EmployeeRequest();
//                    employeeRequest.setKeyword(staffs.getEmail());
                    employeeRequest.setPageSize(10000);
                    employeeRequest.setPageNo(0);
                    employeeRequest.setUserName(request.getUserName());
                    employeeRequest.setPassword(request.getPassword());
                    JsonNode jsonNode = null;
                    try {
                        jsonNode = getListUser(employeeRequest);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    if (jsonNode.isArray() && !jsonNode.isEmpty()) {
                        employeeId = jsonNode.get(0).get("employeeId").asLong();
                    }

                    jsonStaff.put("staffId", employeeId); // ID nhân viên
                    jsonStaff.put("signLevel", staff.getSignLevel()); // Thứ tự ký
                    if(Objects.nonNull(staff.getSignLevel()) && Objects.nonNull(staff.getShowImage()) && staff.getShowImage() != 0){
                        jsonStaff.put("signImage", staff.getSignLevel() + 1); // Có hiển thị chữ ký không
                    }
                    jsonStaff.put("departmentName", staff.getOrganizationName()); // Tên đơn vị lấy từ api getLitsUserSignWithRole
                    jsonStaff.put("departmentSignId", staff.getOrganizationId()); // ID đơn vị lấy từ api getLitsUserSignWithRole
                    jsonStaff.put("signImageId", staff.getSignImageId()); // ID hình chữ ký (nếu có)
                    jsonStaff.put("promulgatingSigner", staff.getPromulgatingSigner()); //Có phải người ban hành không
                    jsonStaff.put("parallelSignGroup", staff.getParallelSignGroup()); // Nhóm ký song song (null nếu không ký song song)
//                    jsonStaff.put("parallelSignLevel", 0); // thu tu ky song song 0,1,2,...
//                    jsonStaff.put("parallelSignPosition", 0); // thu tu ky song song 0,1,2,...
                    jsonStaffs.put(jsonStaff);
                }
            }
            jsonText.put("lstStaff", jsonStaffs);
            // End - Danh sach nguoi ky

            String info = service.getDataFromService("DocumentService.addText", jsonText.toString());
            if (info != null) {
                System.out.println(info);
                return Long.valueOf(info.trim());
            }
        } catch (Exception e) {
            log.error("Error while adding text to VOffice: {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public String sendAndSign(Long id, String userName, String password) {
        try {
            ConnectVOfficeService service = new ConnectVOfficeService(URL, userName, password);
            String transCode = UUID.randomUUID().toString();
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("textId", id); // id van ban trinh ky
            params.put("appCode", "NETPM"); // ma ung dung
            params.put("transCode", transCode); // ma giao dich
            String info = service.getDataFromService("DocumentService.sendAndSign", params);
            if (info != null) {
                System.out.println(info);
                return transCode;
            }
        } catch (Exception e) {
            log.error("Error while sending and signing document in VOffice: {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String decryptedPassword(DecryptRequest request) throws Exception {
        return AESUtil.decryptCryptoJS(request.getPassword());
    }

    @Override
    public JsonNode getListUser(EmployeeRequest request) throws Exception {
        JsonNode localRootNode = null;
        String info = callGetListUser(request);
        if (info != null) {
            localRootNode = readResponse(info);
        }
        return localRootNode;
    }

    @Override
    public List<StaffImageSignDTO> getImageSign(EmployeeRequest request) throws Exception {
        if (StringUtils.isEmpty(request.getIsRequestToSignText())) {
            request.setIsRequestToSignText("0");
        }
        List<StaffImageSignDTO> response = new ArrayList<>();

        String info = callGetListUser(request);
        if (ObjectUtils.isNotEmpty(info)) {
            List<EmployeeDTO> employeeList = mapper.readValue(info, new TypeReference<List<EmployeeDTO>>() {
            });
            EmployeeDTO employeeDTO = null;
            if (!employeeList.isEmpty()) {
                employeeDTO = employeeList.get(0);
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("isRequestToSignText", request.getIsRequestToSignText());
                params.put("signStaffIdV2", employeeDTO.getEmployeeId());
                params.put("signStaffCode", employeeDTO.getEmployeeCode());
                String data = getConnectService(request.getUserName(), request.getPassword()).getDataFromService("imageSignAction.search", params);
                if (data != null) {
                    response = mapper.readValue(data, new TypeReference<List<StaffImageSignDTO>>() {
                    });
                    if (request.getStaffImageSignId() != null) {
                        response = response.stream()
                                .filter(dto -> request.getStaffImageSignId().equals(dto.getStaffImageSignId()))
                                .collect(Collectors.toList());
                    }
                    if (ObjectUtils.isNotEmpty(response)) {
                        for (StaffImageSignDTO dto : response)
                            dto.setBytesRead(callGetFile(dto, request.getUserName(), request.getPassword()));
                    }
                }
            }
        }

        return response;
    }

    private byte[] callGetFile(StaffImageSignDTO employeeDTO, String userName, String password) throws Exception {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("type", employeeDTO.getType());
        params.put("filePath", employeeDTO.getPath()); // Tham so truyen vao
        params.put("storage", employeeDTO.getStorage()); // Tham so truyen vao
        byte[] bytesRead = getConnectService(userName, password).getFileFromService("Files.DownloadContentFile", params);
        return bytesRead;
    }

//    @Override
//    public JsonNode getLitsUserSignWithRole(LitsUserSignWithRoleRequest request) throws Exception {
//        List<Long> lstStaff = new ArrayList<>();
//        for (Long staffId : request.getLstStaff()) {
//            Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new BusinessException("Nhân viên có ID " + staffId + " không tồn tại!"));
//            EmployeeRequest employeeRequest =  new EmployeeRequest();
//            employeeRequest.setKeyword(staff.getEmail());
//            employeeRequest.setPageSize(10000);
//            employeeRequest.setPageNo(0);
//            employeeRequest.setUserName(request.getUserName());
//            employeeRequest.setPassword(request.getPassword());
//            JsonNode jsonNode = getListUser(employeeRequest);
//            if (jsonNode.isArray() && jsonNode.size() > 0) {
//                long employeeId = jsonNode.get(0).get("employeeId").asLong();
//                lstStaff.add(employeeId);
//            }
//        }
//        JsonNode rootNode = null;
//        Map<String, Object> params = new LinkedHashMap<>();
//        params.put("lstStaff", getJSONArrayLong(lstStaff));
//
//        String data = getConnectService(request.getUserName(), request.getPassword()).getDataFromService("DocumentService.getLitsUserSignWithRole", params);
//        if (data != null) {
//            System.out.println(data);
//        }
//        if (data != null) {
//            rootNode = readResponse(data);
//        }
//        return rootNode;
//    }

//    @Override
//    public VofficeUploadFileResponse uploadFileToService(MultipartFile file, String type) throws IOException {
//        File tempFile = File.createTempFile("uploaded_", "_" + file.getOriginalFilename());
//        try {
//            VofficeUploadFileResponse vofficeUploadFileResponse = new VofficeUploadFileResponse();
//            file.transferTo(tempFile);
//            String result = getConnectService("userName", "password").uploadFileToService(tempFile.getAbsolutePath());
//            vofficeUploadFileResponse.setFileName(file.getOriginalFilename());
//            vofficeUploadFileResponse.setFilePath(result);
//            vofficeUploadFileResponse.setFileType(type);
//            return vofficeUploadFileResponse;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (tempFile.exists()) {
//                tempFile.delete();
//            }
//        }
//    }

    private JsonNode readResponse(String info) throws JsonProcessingException {
        return mapper.readTree(info);
    }

    private static JSONArray getJSONArrayLong(List<Long> listLong) {
        JSONArray array = new JSONArray();
        try {
            if (listLong != null && listLong.size() > 0) {
                JSONObject json;
                for (Long obj : listLong) {
                    json = new JSONObject();
                    json.put("staffId", obj);
                    array.put(json);
                }
            }
        } catch (Exception e) {
            log.error("Error while converting list to JSONArray: {}", e.getMessage());
            e.printStackTrace();
        }
        return array;
    }

    private String callGetListUser(EmployeeRequest request) throws Exception {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("keyword", request.getKeyword());
        params.put("pageSize", request.getPageSize());
        params.put("startRecord", request.getPageNo());

        String info = getConnectService(request.getUserName(), request.getPassword()).getDataFromService("staffAction.getListUser", params);
        return info;
    }
    @Override
    public ResultPageResponse<VofficeHistoryResponse> getListHistory(Long programId, SearchHistoryRequest request) throws Exception {
        Pageable pageable = PageRequest.of(request.getPageNo(), request.getPageSize());
        Page<VofficeHistoryResponse> resultPage = null;

        ConnectVOfficeService service = null;

        if(request.getUserName() != null && request.getPassword() != null) {
            service = getConnectService(request.getUserName(), request.getPassword());
        }

        for(VofficeHistoryResponse response : resultPage.getContent()) {
            JSONObject jsonText = new JSONObject();
            try {
                if(service != null) {
                    jsonText.put("textId",response.getDocumentId());
                    jsonText.put("isListFile",0);
                    String dataFromService = service.getDataFromService("textAction.getTextDetail", jsonText.toString());
                    HashMap<String, Object> mapData = objectMapper.readValue(
                            dataFromService,
                            new TypeReference<>() {
                            }
                    );
                    List<VofficeFileDto> fileMainSign = objectMapper.convertValue(mapData.get("fileMainSign"), new TypeReference<>() {
                    });
                    List<VofficeFileDto> fileAttachFromSign = objectMapper.convertValue(mapData.get("fileAttachFromSign"), new TypeReference<>() {
                    });
                    List<VofficeFileDto> listBaseFile = objectMapper.convertValue(mapData.get("listBaseFile"), new TypeReference<>() {
                    });
                    List<VofficeFileDto> fileAttachFromDoc = objectMapper.convertValue(mapData.get("fileAttachFromDoc"), new TypeReference<>() {
                    });
                    response.setFileMainSign(fileMainSign);
                    response.setFileAttachFromSign(fileAttachFromSign);
                    response.setFileAttachFromDoc(fileAttachFromDoc);
                    response.setListBaseFile(listBaseFile);
                }
                List<SignerListEntity> signatureEntities = signerListRepository.findBySignatureDetailId(response.getId());
                response.setSignerListResponses(
                        signatureEntities.stream()
                                .map(SignerListResponse::fromEntity)
                                .collect(Collectors.toList())
                );
                List<DocumentSignEntity> signatureDocumentEntities = documentSignRepository.findBySignatureDetailId(response.getId());
                response.setDocumentSignResponses(
                        signatureDocumentEntities.stream()
                                .map(DocumentSignResponse::fromEntity)
                                .collect(Collectors.toList())
                );
            } catch (JSONException | JsonProcessingException e) {
                System.out.println(e.getMessage());
            }
        }
        ResultPageResponse<VofficeHistoryResponse> pageResponse = new ResultPageResponse<>();
        pageResponse.setPageSize(resultPage.getSize());
        pageResponse.setCurrentPage(resultPage.getNumber());
        pageResponse.setTotalPages(resultPage.getTotalPages());
        pageResponse.setTotalItems(resultPage.getTotalElements());
        pageResponse.setItems(resultPage.getContent());
        return pageResponse;
    }

    @Override
    public InputStreamResource downloadFile(String fileName) {
        try {
            ByteArrayInputStream byteArrayInputStream = minioService.getFile(fileName, minioBucketName);
            // read data file
            if (org.springframework.util.ObjectUtils.isEmpty(byteArrayInputStream)) {
                throw new IllegalArgumentException("");
            }
            return new InputStreamResource(byteArrayInputStream);
        } catch (Exception e) {
            String msg = "downloadFile: " + e.getMessage();
            log.error(msg);
            throw new IllegalArgumentException("");
        }
    }

    @Override
    public User loginVoffice(LoginVofficeRequest request) throws Exception {
        ConnectVOfficeService vOfficeService = getConnectService(request.getUserName(), request.getPassword());
        if (StringUtils.isEmpty(vOfficeService.getSessionId())) {
            throw new IllegalArgumentException("Tài khoản hoặc mật khẩu V-Office không đúng, vui lòng kiểm tra lại!");
        }
        return vOfficeService.getUserInformation();
    }

    @Override
    public InputStreamResource downloadFile(String fileName, String bucketName) {
        try {
            ByteArrayInputStream byteArrayInputStream = minioService.getFile(fileName, bucketName);
            // read data file
            if (org.springframework.util.ObjectUtils.isEmpty(byteArrayInputStream)) {
                throw new IllegalArgumentException("");
            }
            return new InputStreamResource(byteArrayInputStream);
        } catch (Exception e) {
            String msg = "downloadFile: " + e.getMessage();
            log.error(msg);
            throw new IllegalArgumentException("");
        }
    }

    @Override
    public ResultPageResponse<SignatureDetailResponse> getListSignatureDetail(SearchSignatureDetailRequest request){

        Pageable pageable = PageRequest.of(request.getPageNo(), request.getPageSize());
        Page<SignatureDetailResponse> resultPage = null;


        resultPage.getContent().forEach(response -> {
            List<SignerListEntity> signatureEntities = signerListRepository.findBySignatureDetailId(response.getId());
            response.setSignerListResponses(
                    signatureEntities.stream()
                            .map(SignerListResponse::fromEntity)
                            .collect(Collectors.toList())
            );

            List<DocumentSignEntity> signatureDocumentEntities = documentSignRepository.findBySignatureDetailId(response.getId());
            response.setDocumentSignResponses(
                    signatureDocumentEntities.stream()
                            .map(DocumentSignResponse::fromEntity)
                            .collect(Collectors.toList())
            );
        });


        ResultPageResponse<SignatureDetailResponse> pageResponse = new ResultPageResponse<>();
        pageResponse.setPageSize(resultPage.getSize());
        pageResponse.setCurrentPage(resultPage.getNumber());
        pageResponse.setTotalPages(resultPage.getTotalPages());
        pageResponse.setTotalItems(resultPage.getTotalElements());
        pageResponse.setItems(resultPage.getContent());
        return pageResponse;
    }

    @Override
    public JsonNode getListFields(ComboboxRequest comboboxRequest) throws Exception {
        ConnectVOfficeService service = getConnectService(comboboxRequest.getUserName(), comboboxRequest.getPassword());
//        ConnectVOfficeService service = new ConnectVOfficeService(URL, JWT);
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("type", comboboxRequest.getType()); // Tham so mac dinh khong thay doi
        String result = service.getDataFromService("DocumentService.getListFields", params);
        JsonNode localRootNode = null;
        if (result != null) {
            localRootNode = readResponse(result);
        }
        return localRootNode;
    }


//    @Override
//    @Transactional
//    public ReceiveFromVOfficeResponse receiveFromVOfficeEndpoint(ReceiveFromVOfficeRequest request) {
//        ReceiveFromVOfficeResponse response = new ReceiveFromVOfficeResponse();
//        try {
//            Optional<ProgramSignatureDetail> programSignatureDetailOptional = programSignatureDetailRepository.findByTransCode(request.getTransCode());
//            if (programSignatureDetailOptional.isEmpty()) {
//                response.setStatus("FAIL");
//                response.setMessage("No signing information found for transaction code: " + request.getTransCode());
//                return response;
//            }
//
//            ProgramSignatureDetail programSignatureDetail = programSignatureDetailOptional.get();
//
//            String status;
//            if ("5".equals(request.getSignStatus())) {
//                status = VofficeStatus.DA_KY_DUYET.getValue();
//            } else if ("2".equals(request.getSignStatus())) {
//                status = VofficeStatus.DA_TU_CHOI.getValue();
//            } else if ("4".equals(request.getSignStatus())) {
//                status = VofficeStatus.HUY.getValue();
//            } else {
//                status = VofficeStatus.DA_TAO_VAN_BAN_TRINH_KY.getValue();
//            }
//
//            programSignatureDetail.setStatus(status);
//            programSignatureDetailRepository.save(programSignatureDetail);
//
//            response.setStatus("SUCCESS");
//            response.setMessage("Request processed successfully for transCode: " + request.getTransCode());
//            return response;
//
//        } catch (Exception e) {
//            response.setStatus("FAIL");
//            response.setMessage("Error processing request for transCode: " + request.getTransCode() + ". Reason: " + e.getMessage());
//            return response;
//        }
//    }

    @Override
    public byte[] downloadFileVoffice(VOfficeFileRequest request, Long textId) throws Exception {
        ConnectVOfficeService service = getConnectService(request.getUserName(), request.getPassword());
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("documentId", textId);
        params.put("type", 0);
        params.put("filePath", request.getFilePath());
        params.put("storage", request.getStorage());
        params.put("fileName", request.getFileName());
        params.put("isOriginal",0);
        return service.getFileFromService("Files.DownloadContentFile", params);
    }

}
