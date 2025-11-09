package org.example.synergy.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class WorkflowConfigRequest {
    private Long id;
    private String name;
    private Long documentTypeId;
    private String description;

    // SLA chung
    private Integer maxSla;
    private Integer warningSla;
    private String warningPerson;

    private List<WorkflowStepRequest> steps;
}
