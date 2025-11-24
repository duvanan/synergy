package org.example.synergy.service;

import java.io.ByteArrayInputStream;

import org.example.synergy.dto.response.AttachFileResponseDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author truongbx7
 * @project vss-fw
 * @date 6/13/2023
 */
public interface MinioService {
    String getPresignedUrl(String fileName, String bucketName);
    void deleteFile(String fileName, String bucketName);
    ByteArrayInputStream getInputStreamTemplate(String fileName, String bucketName);
    boolean bucketExists(String bucketName);
    void makeBucket(String bucketName);
    ByteArrayInputStream getFile(String fileName, String bucketName);
    String uploadFile(MultipartFile file, String bucketName, String tmpName);

    String uploadFileInputStreamResource(InputStreamResource inputStreamResource, String bucketName, String tmpName);

    AttachFileResponseDto uploadFile(MultipartFile file, String extensionFile);
    InputStreamResource downloadFile(String fileName);
}
