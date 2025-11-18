package org.example.synergy.repository;

import org.example.synergy.entity.AppraisalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppraisalRequestRepository extends JpaRepository<AppraisalRequest, Long> {
    boolean existsByRequestCode(String requestCode);
}
