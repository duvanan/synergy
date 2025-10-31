package org.example.synergy.repository;

import org.example.synergy.entity.DocumentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {

    @Query("""
        SELECT d FROM DocumentType d
        WHERE (:name IS NULL OR LOWER(d.documentTypeName) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:label IS NULL OR LOWER(d.label) LIKE LOWER(CONCAT('%', :label, '%')))
    """)
    Page<DocumentType> search(
            @Param("name") String name,
            @Param("label") String label,
            Pageable pageable
    );
}
