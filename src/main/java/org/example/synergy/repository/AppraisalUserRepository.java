package org.example.synergy.repository;

import org.example.synergy.entity.AppraisalUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppraisalUserRepository extends JpaRepository<AppraisalUserEntity, Long> {

}
