package org.example.synergy.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.synergy.dto.request.WorkflowConfigRequest;
import org.example.synergy.dto.request.WorkflowStepRequest;
import org.example.synergy.entity.WorkflowConfig;
import org.example.synergy.entity.WorkflowStep;
import org.example.synergy.repository.WorkflowConfigRepository;
import org.example.synergy.repository.WorkflowStepRepository;
import org.example.synergy.service.WorkflowConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkflowConfigServiceImpl implements WorkflowConfigService {

    private final WorkflowConfigRepository configRepo;
    private final WorkflowStepRepository stepRepo;

    @Override
    public List<WorkflowConfig> findAll() {
        return configRepo.findAll();
    }

    @Override
    public WorkflowConfig findById(Long id) {
        return configRepo.findById(id).orElseThrow(() -> new RuntimeException("Workflow not found"));
    }

    @Override
    @Transactional
    public WorkflowConfig save(WorkflowConfigRequest req) {
        WorkflowConfig cfg = toEntity(req);
        WorkflowConfig saved = configRepo.save(cfg);
        saveSteps(saved.getId(), req.getSteps());
        return saved;
    }

    @Override
    @Transactional
    public WorkflowConfig update(Long id, WorkflowConfigRequest req) {
        WorkflowConfig existing = findById(id);
        updateEntity(existing, req);
        WorkflowConfig updated = configRepo.save(existing);

        stepRepo.deleteByWorkflowConfigId(id);
        saveSteps(id, req.getSteps());
        return updated;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        stepRepo.deleteByWorkflowConfigId(id);
        configRepo.deleteById(id);
    }

    @Override
    public List<WorkflowStep> findSteps(Long configId) {
        return stepRepo.findByWorkflowConfigId(configId);
    }

    // ---------------------
    private void saveSteps(Long configId, List<WorkflowStepRequest> requests) {
        if (requests == null || requests.isEmpty()) return;

        List<WorkflowStep> steps = requests.stream().map(r -> {
            WorkflowStep s = new WorkflowStep();
            s.setWorkflowConfigId(configId);
            s.setStepNumber(r.getStepNumber());
            s.setSubStepNumber(r.getSubStepNumber());
            s.setDepartmentId(r.getDepartmentId());
            s.setPic(r.getPic());
            s.setStepMaxSla(r.getStepMaxSla());
            s.setStepWarningSla(r.getStepWarningSla());
            s.setStepWarningPerson(r.getStepWarningPerson());
            return s;
        }).toList();

        stepRepo.saveAll(steps);
    }

    private WorkflowConfig toEntity(WorkflowConfigRequest req) {
        WorkflowConfig c = new WorkflowConfig();
        if (req.getId() != null) c.setId(req.getId());
        c.setDocumentTypeId(req.getDocumentTypeId());
        c.setMaxSla(req.getMaxSla());
        c.setWarningSla(req.getWarningSla());
        c.setWarningPerson(req.getWarningPerson());
        c.setDescription(req.getDescription());
        return c;
    }

    private void updateEntity(WorkflowConfig existing, WorkflowConfigRequest req) {
        existing.setDocumentTypeId(req.getDocumentTypeId());
        existing.setMaxSla(req.getMaxSla());
        existing.setWarningSla(req.getWarningSla());
        existing.setWarningPerson(req.getWarningPerson());
        existing.setDescription(req.getDescription());
    }
}
