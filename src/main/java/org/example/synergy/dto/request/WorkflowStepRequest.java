package org.example.synergy.dto.request;

import lombok.Data;

@Data
public class WorkflowStepRequest {
    private Integer stepNumber;
    private Integer subStepNumber;
    private String departmentId;
    private String pic;
    private Integer stepMaxSla;
    private Integer stepWarningSla;
    private String stepWarningPerson; // nhiều mã cách nhau dấu phẩy
}
