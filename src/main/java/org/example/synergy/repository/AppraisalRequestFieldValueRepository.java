package org.example.synergy.repository;

import org.example.synergy.entity.AppraisalRequestFieldValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppraisalRequestFieldValueRepository extends JpaRepository<AppraisalRequestFieldValue, Long> {
    List<AppraisalRequestFieldValue> findByAppraisalRequestId(Long appraisalRequestId);
}
