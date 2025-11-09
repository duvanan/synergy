package org.example.synergy.dto.request;

import lombok.Data;

@Data
public class WorkflowStepRequest {
    private Integer stepNumber;
    private String label;
    private String stepType;
    private Double minValue;
    private Double maxValue;
    private String unit;
    private String tooltip;

    // SLA riêng cho bước
    private Integer stepMaxSla;
    private Integer stepWarningSla;
    private String stepWarningPerson;

    private String departmentId;
    private String pic;
}
