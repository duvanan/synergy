package org.example.synergy.dto.response;

import lombok.Data;

@Data
public class DocumentTypeAttributeResponse {
    private Long id;
    private String label;
    private String fieldCode;
    private Boolean required;
    private String dataType;
    private String defaultValue;
    private String minValue;
    private String maxValue;
    private String unitList;
    private String tooltip;
    private String valueList;
}