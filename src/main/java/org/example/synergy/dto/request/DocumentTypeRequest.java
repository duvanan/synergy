package org.example.synergy.dto.request;

import lombok.Data;
import java.util.List;

/** DTO dùng cho tạo / cập nhật loại văn bản */
@Data
public class DocumentTypeRequest {
    private String name;
    private String code;
    private String description;
    private String templateFilePath; // hoặc upload sau
    private List<DocumentTypeAttributeRequest> attributes;
}