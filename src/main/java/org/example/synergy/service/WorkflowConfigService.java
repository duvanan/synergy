package org.example.synergy.service;

import org.example.synergy.dto.request.WorkflowConfigRequest;
import org.example.synergy.dto.response.WorkflowConfigResponse;
import org.example.synergy.entity.WorkflowConfig;
import org.example.synergy.entity.WorkflowStep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WorkflowConfigService {
    List<WorkflowConfig> findAll();
    WorkflowConfig findById(Long id);
    WorkflowConfig save(WorkflowConfigRequest request);
    WorkflowConfig update(Long id, WorkflowConfigRequest request);
    void delete(Long id);
    List<WorkflowStep> findSteps(Long configId);

    Page<WorkflowConfigResponse> filterWorkflowConfigs(String name, Long documentTypeId, Integer maxSla, Pageable pageable);}
