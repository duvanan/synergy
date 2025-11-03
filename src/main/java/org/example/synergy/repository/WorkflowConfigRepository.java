package org.example.synergy.repository;

import org.example.synergy.entity.WorkflowConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowConfigRepository extends JpaRepository<WorkflowConfig, Long> {
}
