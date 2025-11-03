package org.example.synergy.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.synergy.dto.request.PartnerRequest;
import org.example.synergy.entity.Partner;
import org.example.synergy.entity.PartnerRelation;
import org.example.synergy.repository.PartnerRelationRepository;
import org.example.synergy.repository.PartnerRepository;
import org.example.synergy.service.PartnerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;
    private final PartnerRelationRepository relationRepository;

    // -----------------------------
    // CRUD METHODS
    // -----------------------------

    @Override
    public List<Partner> findAll() {
        return partnerRepository.findAll();
    }

    @Override
    public Partner findById(Long id) {
        return partnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found with id " + id));
    }

    @Override
    @Transactional
    public Partner save(PartnerRequest req) {
        Partner partner = toEntity(req);
        Partner saved = partnerRepository.save(partner);
        saveRelations(saved.getId(), req);
        return saved;
    }

    @Override
    @Transactional
    public Partner update(Long id, PartnerRequest req) {
        Partner existing = findById(id);
        updateEntity(existing, req);
        Partner updated = partnerRepository.save(existing);

        relationRepository.deleteByPartnerId(id);
        saveRelations(id, req);

        return updated;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        relationRepository.deleteByPartnerId(id);
        partnerRepository.deleteById(id);
    }

    // -----------------------------
    // SEARCH & RELATION
    // -----------------------------

    @Override
    public Page<Partner> search(String name, String type, Pageable pageable) {
        return partnerRepository.search(name, type, pageable);
    }

    @Override
    public List<PartnerRelation> findRelations(Long partnerId) {
        return relationRepository.findByPartnerId(partnerId);
    }

    // -----------------------------
    // HELPER METHODS
    // -----------------------------

    private void saveRelations(Long partnerId, PartnerRequest req) {
        if (req.getRelatedPersons() == null || req.getRelatedPersons().isEmpty()) return;

        List<PartnerRelation> relations = req.getRelatedPersons().stream().map(r -> {
            PartnerRelation pr = new PartnerRelation();
            pr.setPartnerId(partnerId);
            pr.setEmployeeName(r.getEmployeeName());
            pr.setEmployeeCode(r.getEmployeeCode());
            pr.setRelationship(r.getRelationship());
            return pr;
        }).toList();

        relationRepository.saveAll(relations);
    }

    private Partner toEntity(PartnerRequest req) {
        Partner p = new Partner();
        if (req.getId() != null) p.setId(req.getId());

        p.setType(req.getType());
        p.setName(req.getName());
        p.setPartnerType(req.getPartnerType());
        p.setTaxCode(req.getTaxCode());
        p.setInvoiceAddress(req.getInvoiceAddress());
        p.setInvoiceEmail(req.getInvoiceEmail());
        p.setLegalRepresentativeName(req.getLegalRepresentativeName());
        p.setLegalRepresentativeId(req.getLegalRepresentativeId());
        p.setLegalRepresentativeAddress(req.getLegalRepresentativeAddress());
        p.setLegalRepresentativePhone(req.getLegalRepresentativePhone());
        p.setCccd(req.getCccd());
        p.setContactInfo(req.getContactInfo());
        p.setConnected(req.getConnected());
        return p;
    }

    private void updateEntity(Partner p, PartnerRequest req) {
        p.setType(req.getType());
        p.setName(req.getName());
        p.setPartnerType(req.getPartnerType());
        p.setTaxCode(req.getTaxCode());
        p.setInvoiceAddress(req.getInvoiceAddress());
        p.setInvoiceEmail(req.getInvoiceEmail());
        p.setLegalRepresentativeName(req.getLegalRepresentativeName());
        p.setLegalRepresentativeId(req.getLegalRepresentativeId());
        p.setLegalRepresentativeAddress(req.getLegalRepresentativeAddress());
        p.setLegalRepresentativePhone(req.getLegalRepresentativePhone());
        p.setCccd(req.getCccd());
        p.setContactInfo(req.getContactInfo());
        p.setConnected(req.getConnected());
    }
}
