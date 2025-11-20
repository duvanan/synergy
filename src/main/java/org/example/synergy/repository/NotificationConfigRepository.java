package org.example.synergy.repository;

import org.example.synergy.entity.NotificationConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationConfigRepository extends JpaRepository<NotificationConfig, Long> {

    Page<NotificationConfig> findByDocumentTypeId(Long documentTypeId, Pageable pageable);
}
