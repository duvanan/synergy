package org.example.synergy.repository;

import org.example.synergy.entity.AppraisalRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppraisalRequestRepository extends JpaRepository<AppraisalRequest, Long> {
    boolean existsByRequestCode(String requestCode);

    @Query("SELECT r FROM AppraisalRequest r " +
            "WHERE (:requestCode IS NULL OR r.requestCode LIKE %:requestCode%) " +
            "AND (:status IS NULL OR r.status = :status)" +
            "AND (:documentTypeId IS NULL OR r.documentTypeId = :documentTypeId)")
    Page<AppraisalRequest> search(
            @Param("requestCode") String requestCode,
            @Param("documentTypeId") Long documentTypeId,
            @Param("status") String status,
            Pageable pageable);


}
