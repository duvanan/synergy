package org.example.synergy.dto.request;

import lombok.Data;

@Data
public class DocumentTypeAttributeRequest {
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