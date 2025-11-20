package org.example.synergy.service;

import org.example.synergy.dto.request.PartnerRequest;
import org.example.synergy.entity.Partner;
import org.example.synergy.entity.PartnerRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PartnerService {
    List<Partner> findAll();
    Partner findById(Long id);
    Partner save(PartnerRequest request);
    Partner update(Long id, PartnerRequest request);
    void delete(Long id);
    Page<Partner> search(String name, String type, Pageable pageable);
    List<PartnerRelation> findRelations(Long partnerId);
}
