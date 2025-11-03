package org.example.synergy.service;

import org.example.synergy.dto.request.WorkflowConfigRequest;
import org.example.synergy.entity.WorkflowConfig;
import org.example.synergy.entity.WorkflowStep;

import java.util.List;

public interface WorkflowConfigService {
    List<WorkflowConfig> findAll();
    WorkflowConfig findById(Long id);
    WorkflowConfig save(WorkflowConfigRequest request);
    WorkflowConfig update(Long id, WorkflowConfigRequest request);
    void delete(Long id);
    List<WorkflowStep> findSteps(Long configId);
}
