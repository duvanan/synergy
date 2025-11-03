package org.example.synergy.repository;

import org.example.synergy.entity.PartnerRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerRelationRepository extends JpaRepository<PartnerRelation, Long> {
    List<PartnerRelation> findByPartnerId(Long partnerId);
    void deleteByPartnerId(Long partnerId);
}
