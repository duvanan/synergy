package org.example.synergy.repository;

import org.example.synergy.entity.WorkflowStep;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkflowStepRepository extends JpaRepository<WorkflowStep, Long> {
    List<WorkflowStep> findByWorkflowConfigId(Long configId);
    void deleteByWorkflowConfigId(Long configId);
}
