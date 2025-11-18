package org.example.synergy.repository;

import org.example.synergy.entity.AppraisalRequestFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppraisalRequestFileRepository extends JpaRepository<AppraisalRequestFile, Long> {
    List<AppraisalRequestFile> findByAppraisalRequestId(Long appraisalRequestId);
}
