package org.example.synergy.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/** DTO trả về dữ liệu loại văn bản */
@Data
public class DocumentTypeResponse {
    private Long id;
    private String name;
    private String code;
    private String description;
    private String templateFilePath;
    private String fileName;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<DocumentTypeAttributeResponse> attributes;
}