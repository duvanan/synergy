package org.example.synergy.repository;

import org.example.synergy.entity.SignerListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignerListRepository extends JpaRepository<SignerListEntity, Long> {

    List<SignerListEntity> findBySignatureDetailId(Long signatureDetailId);
}
