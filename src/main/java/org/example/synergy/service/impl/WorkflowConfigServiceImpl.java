package org.example.synergy.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.synergy.contants.RolePositionConstant;
import org.example.synergy.dto.request.WorkflowConfigRequest;
import org.example.synergy.dto.request.WorkflowStepRequest;
import org.example.synergy.dto.response.WorkflowConfigResponse;
import org.example.synergy.dto.response.WorkflowStepResponse;
import org.example.synergy.entity.WorkflowConfig;
import org.example.synergy.entity.WorkflowStep;
import org.example.synergy.repository.DepartmentRepository;
import org.example.synergy.repository.UserRepository;
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
    private final DepartmentRepository departmentRepo;
    private final UserRepository userRepo;

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

    // ------------------------------
    // NEW saveSteps() MATCH UI NEW
    // ------------------------------

    private void saveSteps(WorkflowConfig config, List<WorkflowStepRequest> requests) {
        if (requests == null || requests.isEmpty()) return;

        List<WorkflowStep> steps = requests.stream().map(r -> {
            WorkflowStep s = new WorkflowStep();
            s.setWorkflowConfig(config);

            // --- fields theo UI mới ---
            s.setParentStep(r.getParentStep());
            s.setChildStep(r.getChildStep());
            s.setDepartmentId(r.getDepartmentId());
            s.setPic(r.getPic());
            s.setIsLeadUnit(r.getIsLeadUnit());

            s.setStepMaxSla(r.getStepMaxSla());
            s.setStepWarningSla(r.getStepWarningSla());
            s.setStepWarningPerson(r.getStepWarningPerson());

            s.setTooltip(r.getTooltip());

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

    public Page<WorkflowConfigResponse> filterWorkflowConfigs(
            String name, Long documentTypeId, Integer maxSla, Pageable pageable
    ) {
        var spec = WorkflowConfigSpecification.filter(name, documentTypeId, maxSla);

        Page<WorkflowConfig> page = configRepo.findAll(spec, pageable);

        return page.map(this::mapToDto);
    }


    private WorkflowConfigResponse mapToDto(WorkflowConfig entity) {
        WorkflowConfigResponse dto = new WorkflowConfigResponse();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDocumentTypeId(entity.getDocumentTypeId());
        dto.setDescription(entity.getDescription());
        dto.setMaxSla(entity.getMaxSla());
        dto.setWarningSla(entity.getWarningSla());
        dto.setWarningPerson(entity.getWarningPerson());

        List<WorkflowStepResponse> steps = entity.getSteps()
                .stream().map(this::mapStepToDto).toList();

        dto.setSteps(steps);
        return dto;
    }

    private WorkflowStepResponse mapStepToDto(WorkflowStep step) {
        WorkflowStepResponse dto = new WorkflowStepResponse();

        dto.setId(step.getId());
        dto.setParentStep(step.getParentStep());
        dto.setChildStep(step.getChildStep());
        dto.setDepartmentId(step.getDepartmentId());
        dto.setPic(step.getPic());
        dto.setIsLeadUnit(step.getIsLeadUnit());
        dto.setStepMaxSla(step.getStepMaxSla());
        dto.setStepWarningSla(step.getStepWarningSla());
        dto.setStepWarningPerson(step.getStepWarningPerson());
        dto.setTooltip(step.getTooltip());

        // --- Lấy thêm thông tin phòng ban ---
        departmentRepo.findById(step.getDepartmentId()).ifPresent(dep -> {
            dto.setDepartmentName(dep.getName());
        });

        // --- Lấy thông tin nhân sự ---
        userRepo.findByUserCode(step.getPic()).ifPresent(user -> {
            dto.setPicName(user.getFullName());

            String positionCode = user.getStaffPosition();
            String positionName = RolePositionConstant.getPositionName(positionCode);

            dto.setPicPosition(positionName);
        });


        return dto;
    }

}
