package org.example.synergy.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.synergy.dto.request.WorkflowConfigRequest;
import org.example.synergy.dto.request.WorkflowStepRequest;
import org.example.synergy.entity.WorkflowConfig;
import org.example.synergy.entity.WorkflowStep;
import org.example.synergy.repository.WorkflowConfigRepository;
import org.example.synergy.repository.WorkflowStepRepository;
import org.example.synergy.service.WorkflowConfigService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return configRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Workflow config not found"));
    }

    @Override
    @Transactional
    public WorkflowConfig save(WorkflowConfigRequest req) {
        WorkflowConfig cfg = toEntity(req);
        WorkflowConfig saved = configRepo.save(cfg);
        saveSteps(saved, req.getSteps());
        return saved;
    }

    @Override
    @Transactional
    public WorkflowConfig update(Long id, WorkflowConfigRequest req) {
        WorkflowConfig existing = findById(id);
        updateEntity(existing, req);
        WorkflowConfig updated = configRepo.save(existing);

        stepRepo.deleteByWorkflowConfigId(id);
        saveSteps(updated, req.getSteps());
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

    private void saveSteps(WorkflowConfig config, List<WorkflowStepRequest> requests) {
        if (requests == null || requests.isEmpty()) return;

        List<WorkflowStep> steps = requests.stream().map(r -> {
            WorkflowStep s = new WorkflowStep();
            s.setWorkflowConfig(config);
            s.setStepNumber(r.getStepNumber());
            s.setLabel(r.getLabel());
            s.setStepType(r.getStepType());
            s.setMinValue(r.getMinValue());
            s.setMaxValue(r.getMaxValue());
            s.setUnit(r.getUnit());
            s.setTooltip(r.getTooltip());
            s.setStepMaxSla(r.getStepMaxSla());
            s.setStepWarningSla(r.getStepWarningSla());
            s.setStepWarningPerson(r.getStepWarningPerson());
            s.setDepartmentId(r.getDepartmentId());
            s.setPic(r.getPic());
            return s;
        }).toList();

        stepRepo.saveAll(steps);
    }

    private WorkflowConfig toEntity(WorkflowConfigRequest req) {
        WorkflowConfig c = new WorkflowConfig();
        if (req.getId() != null) c.setId(req.getId());
        updateEntity(c, req);
        return c;
    }

    private void updateEntity(WorkflowConfig existing, WorkflowConfigRequest req) {
        existing.setName(req.getName());
        existing.setDocumentTypeId(req.getDocumentTypeId());
        existing.setDescription(req.getDescription());
        existing.setMaxSla(req.getMaxSla());
        existing.setWarningSla(req.getWarningSla());
        existing.setWarningPerson(req.getWarningPerson());
    }

    public Page<WorkflowConfig> filterWorkflowConfigs(String name, Long documentTypeId, Integer maxSla, Pageable pageable) {
        return configRepo.findAll(WorkflowConfigSpecification.filter(name, documentTypeId, maxSla), pageable);
    }
}
