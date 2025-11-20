package org.example.synergy.controller;

import lombok.RequiredArgsConstructor;
import org.example.synergy.dto.request.DocumentTypeRequest;
import org.example.synergy.dto.response.DocumentTypeResponse;
import org.example.synergy.service.DocumentTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller quản lý loại văn bản
 */
@RestController
@RequestMapping("/api/document-types")
@RequiredArgsConstructor
public class DocumentTypeController {

    private final DocumentTypeService service;

    @GetMapping
    public ResponseEntity<List<DocumentTypeResponse>> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String code
    ) {
        return ResponseEntity.ok(service.filter(keyword, code));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentTypeResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<DocumentTypeResponse> create(@RequestBody DocumentTypeRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentTypeResponse> update(
            @PathVariable Long id,
            @RequestBody DocumentTypeRequest request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
