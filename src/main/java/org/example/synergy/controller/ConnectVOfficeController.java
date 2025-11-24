package org.example.synergy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.synergy.dto.request.VOfficeFileRequest;
import org.example.synergy.dto.request.voffice.*;
import org.example.synergy.dto.response.ResultPageResponse;
import org.example.synergy.dto.response.SignatureDetailResponse;
import org.example.synergy.dto.response.StaffImageSignDTO;
import org.example.synergy.dto.response.VofficeHistoryResponse;
import org.example.synergy.service.VOfficeService;
import org.example.synergy.utils.voffice.User;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("connect-voffice")
@AllArgsConstructor
public class ConnectVOfficeController {
    private final VOfficeService vOfficeService;

    @PostMapping(value = "add-text")
    public ResponseEntity<Long> searchInvestmentPortfolio(@Valid @ModelAttribute AddTextRequest request) throws Exception {
        return ResponseEntity.ok(vOfficeService.addText(request));
    }

    @PostMapping(value = "list-user")
    public ResponseEntity<JsonNode> getListUser(@RequestBody EmployeeRequest request) throws Exception {
        return ResponseEntity.ok(vOfficeService.getListUser(request));
    }

    @PostMapping(value = "imageSign")
    public ResponseEntity<List<StaffImageSignDTO>> getImageSign(@RequestBody EmployeeRequest request) throws Exception {
        return ResponseEntity.ok(vOfficeService.getImageSign(request));
    }

//    @PostMapping(value = "litsUserSignWithRole")
//    public ResponseEntity<JsonNode> getLitsUserSignWithRole(@RequestBody LitsUserSignWithRoleRequest request) throws Exception {
//        return ResponseEntity.ok(vOfficeService.getLitsUserSignWithRole(request));
//    }

//    @PostMapping(value = "uploadFileToService")
//    public ResponseEntity<ResponseDto<VofficeUploadFileResponse>> uploadFileToService(@RequestParam(value = "file") MultipartFile file,
//                                                                                      @RequestParam String type) throws IOException {
//        return ResponseConfig.success(vOfficeService.uploadFileToService(file, type));
//    }

    @PostMapping(value = "decrypted")
    public ResponseEntity<String> decryptedPassword(@RequestBody DecryptRequest request) throws Exception {
        return ResponseEntity.ok(vOfficeService.decryptedPassword(request));
    }

    @PostMapping(value = "history")
    public ResponseEntity<ResultPageResponse<VofficeHistoryResponse>> getListHistory(@RequestBody SearchHistoryRequest request) throws Exception {
        return ResponseEntity.ok(vOfficeService.getListHistory(request.getProgramId(), request));
    }

    @PostMapping(value = "download-file")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam String fileName) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=construction.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(vOfficeService.downloadFile(fileName));
    }

    @PostMapping(value = "login-voffice")
    public ResponseEntity<User> searchInvestmentPortfolio(@Valid @RequestBody LoginVofficeRequest request) throws Exception {
        return ResponseEntity.ok(vOfficeService.loginVoffice(request));
    }

    @GetMapping(value = "download-image")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam String fileName, @RequestParam String bucketName) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(vOfficeService.downloadFile(fileName,bucketName));
    }

    @PostMapping(value = "history-signature-detail")
    public ResponseEntity<ResultPageResponse<SignatureDetailResponse>> getListSignatureDetail(@RequestBody SearchSignatureDetailRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(vOfficeService.getListSignatureDetail( request));
    }

    @PostMapping(value = "list-fields")
    public ResponseEntity<JsonNode> getListFields(@RequestBody ComboboxRequest request) throws Exception {
        return ResponseEntity.ok(vOfficeService.getListFields( request));
    }

//    @GetMapping(value = "export-appendix")
//    public ResponseEntity<InputStreamResource> exportAppendix(@RequestParam Long programId) throws IOException {
//        return vOfficeService.exportAppendix(programId);
//    }

    @PostMapping(value = "download-file-voffice/{textId}")
    public ResponseEntity<byte[]> downloadFile(@RequestBody VOfficeFileRequest request,
                                               @PathVariable("textId") Long textId) throws Exception {
        return ResponseEntity.ok(vOfficeService.downloadFileVoffice(request,textId));
    }
}
