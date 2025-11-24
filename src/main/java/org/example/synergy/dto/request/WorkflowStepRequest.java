package org.example.synergy.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkflowStepRequest {

    private Integer parentStep;
    private Integer childStep;

    private Long departmentId;
    private String pic;

    private Boolean isLeadUnit;

    private Integer stepMaxSla;
    private Integer stepWarningSla;
    private String stepWarningPerson;

    private String tooltip;
}
