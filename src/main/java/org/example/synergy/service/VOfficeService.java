package org.example.synergy.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.synergy.dto.request.VOfficeFileRequest;
import org.example.synergy.dto.request.voffice.*;
import org.example.synergy.dto.response.ResultPageResponse;
import org.example.synergy.dto.response.SignatureDetailResponse;
import org.example.synergy.dto.response.StaffImageSignDTO;
import org.example.synergy.dto.response.VofficeHistoryResponse;
import org.example.synergy.utils.voffice.User;
import org.springframework.core.io.InputStreamResource;

import java.util.List;

public interface VOfficeService {
    Long addText(AddTextRequest request) throws Exception;

    String decryptedPassword(DecryptRequest request) throws Exception;

    JsonNode getListUser(EmployeeRequest request) throws Exception;

    List<StaffImageSignDTO> getImageSign(EmployeeRequest request) throws Exception;

//    JsonNode getLitsUserSignWithRole(LitsUserSignWithRoleRequest request) throws Exception;

//    VofficeUploadFileResponse uploadFileToService(MultipartFile file, String type) throws IOException;

    ResultPageResponse<VofficeHistoryResponse> getListHistory(Long programId, SearchHistoryRequest request) throws Exception;

    InputStreamResource downloadFile(String fileName);

    User loginVoffice(LoginVofficeRequest request) throws Exception;

    InputStreamResource downloadFile(String fileName, String bucketName);

    ResultPageResponse<SignatureDetailResponse> getListSignatureDetail(SearchSignatureDetailRequest request);

    JsonNode getListFields(ComboboxRequest request) throws Exception;
//
//    ResponseEntity<InputStreamResource> exportAppendix(Long programId) throws IOException;

//    ReceiveFromVOfficeResponse receiveFromVOfficeEndpoint(ReceiveFromVOfficeRequest request);

    byte[] downloadFileVoffice(VOfficeFileRequest request, Long textId) throws Exception;
}
