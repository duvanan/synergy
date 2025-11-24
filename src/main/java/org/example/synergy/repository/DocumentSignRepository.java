package org.example.synergy.repository;

import org.example.synergy.entity.DocumentSignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentSignRepository extends JpaRepository<DocumentSignEntity, Long> {

    List<DocumentSignEntity> findBySignatureDetailId(Long signatureDetailId);
}
