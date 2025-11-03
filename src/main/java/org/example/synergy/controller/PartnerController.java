package org.example.synergy.controller;

import lombok.RequiredArgsConstructor;
import org.example.synergy.dto.request.PartnerRequest;
import org.example.synergy.entity.Partner;
import org.example.synergy.entity.PartnerRelation;
import org.example.synergy.service.PartnerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;

    @GetMapping
    public List<Partner> getAll() {
        return partnerService.findAll();
    }

    @GetMapping("/{id}")
    public Partner getById(@PathVariable Long id) {
        return partnerService.findById(id);
    }

    @GetMapping("/{id}/relations")
    public List<PartnerRelation> getRelations(@PathVariable Long id) {
        return partnerService.findRelations(id);
    }

    @PostMapping
    public Partner create(@RequestBody PartnerRequest request) {
        return partnerService.save(request);
    }

    @PutMapping("/{id}")
    public Partner update(@PathVariable Long id, @RequestBody PartnerRequest request) {
        return partnerService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        partnerService.delete(id);
    }

    @GetMapping("/search")
    public Page<Partner> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return partnerService.search(name, type, PageRequest.of(page, size));
    }

}
