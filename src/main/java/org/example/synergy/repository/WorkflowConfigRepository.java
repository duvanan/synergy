package org.example.synergy.repository;

import org.example.synergy.entity.WorkflowConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WorkflowConfigRepository extends JpaRepository<WorkflowConfig, Long>,
        JpaSpecificationExecutor<WorkflowConfig> {

}
