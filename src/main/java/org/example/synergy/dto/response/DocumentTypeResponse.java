package org.example.synergy.dto.response;

import lombok.Data;
import java.util.List;

/** DTO trả về dữ liệu loại văn bản */
@Data
public class DocumentTypeResponse {
    private Long id;
    private String name;
    private String code;
    private String description;
    private String templateFilePath;
    private List<DocumentTypeAttributeResponse> attributes;
}