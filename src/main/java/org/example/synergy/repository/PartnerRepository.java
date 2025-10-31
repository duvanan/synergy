package org.example.synergy.repository;

import org.example.synergy.entity.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

    @Query("""
        SELECT p FROM Partner p
        WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
        AND (:type IS NULL OR LOWER(p.partnerType) LIKE LOWER(CONCAT('%', :type, '%')))
    """)
    Page<Partner> search(String name, String type, Pageable pageable);
}
