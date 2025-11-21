package org.example.synergy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.synergy.dto.response.AttachFileResponseDto;
import org.example.synergy.service.MinioService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Slf4j
public class AttachFileController {

    private final MinioService minioService;

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "extensionFile", required = false) String extensionFile
    ) {
        try {
            // Nếu client không truyền extensionFile thì tự lấy từ tên file
            if (extensionFile == null || extensionFile.isBlank()) {
                String originalName = file.getOriginalFilename();
                if (originalName != null && originalName.contains(".")) {
                    extensionFile = originalName.substring(originalName.lastIndexOf('.') + 1);
                } else {
                    // fallback: mặc định không có đuôi
                    extensionFile = "";
                }
            }

            AttachFileResponseDto responseDto = minioService.uploadFile(file, extensionFile);

            if (responseDto == null) {
                return ResponseEntity.internalServerError()
                        .body("Upload file thất bại");
            }

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            log.error("Error upload file: ", e);
            return ResponseEntity.internalServerError()
                    .body("Có lỗi khi upload file: " + e.getMessage());
        }
    }

    @GetMapping(value = "download")
    public ResponseEntity<InputStreamResource> downloadFileContraction(@RequestParam String fileName) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(minioService.downloadFile(fileName));
    }
}
