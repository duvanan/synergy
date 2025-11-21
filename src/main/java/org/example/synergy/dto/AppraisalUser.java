package org.example.synergy.dto;

import lombok.Data;
import org.example.synergy.dto.request.DocumentTypeAttributeRequest;

import java.util.List;

/** DTO dùng cho tạo / cập nhật loại văn bản */
@Data
public class AppraisalUser {
    private String name;
    private String code;
    private Integer level;
    private String organization;
    private String role;
    private Boolean hostUnit;
}