package org.example.synergy.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class WorkflowConfigResponse {

    private Long id;
    private String name;
    private Long documentTypeId;
    private String description;

    private Integer maxSla;
    private Integer warningSla;
    private String warningPerson;

    private List<WorkflowStepResponse> steps;
}
