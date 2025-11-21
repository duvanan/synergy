package org.example.synergy.repository;

import org.example.synergy.entity.AppraisalUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppraisalUserRepository extends JpaRepository<AppraisalUserEntity, Long> {

    Optional<AppraisalUserEntity> findByCodeAndAndAppraisalRequestId(String code, Long AppraisalRequestId);
    List<AppraisalUserEntity> findAllByAppraisalRequestId(Long AppraisalRequestId);

    List<AppraisalUserEntity> findAllByAppraisalRequestIdAndIsDeleted(Long appraisalRequestId, Boolean isDeleted);


}
