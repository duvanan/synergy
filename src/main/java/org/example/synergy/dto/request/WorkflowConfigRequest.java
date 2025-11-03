package org.example.synergy.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class WorkflowConfigRequest {
    private Long id;
    private Long documentTypeId;
    private Integer maxSla;
    private Integer warningSla;
    private String warningPerson;
    private String description;
    private List<WorkflowStepRequest> steps;
}
