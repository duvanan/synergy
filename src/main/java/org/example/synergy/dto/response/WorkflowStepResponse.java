package org.example.synergy.dto.response;

import lombok.Data;

@Data
public class WorkflowStepResponse {

    private Long id;

    private Integer parentStep;
    private Integer childStep;

    private Long departmentId;
    private String departmentName;

    private String pic;
    private String picName;
    private String picPosition;

    private Boolean isLeadUnit;

    private Integer stepMaxSla;
    private Integer stepWarningSla;
    private String stepWarningPerson;

    private String tooltip;
}
