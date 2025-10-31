package org.example.synergy.controller;

import lombok.RequiredArgsConstructor;
import org.example.synergy.entity.DocumentType;
import org.example.synergy.service.DocumentTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/document-types")
@RequiredArgsConstructor
public class DocumentTypeController {

    private final DocumentTypeService service;

    @GetMapping
    public ResponseEntity<List<DocumentType>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentType> getById(@PathVariable Long id) {
        DocumentType type = service.findById(id);
        return type != null ? ResponseEntity.ok(type) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DocumentType> create(@RequestBody DocumentType documentType) {
        return ResponseEntity.ok(service.save(documentType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentType> update(@PathVariable Long id, @RequestBody DocumentType documentType) {
        return ResponseEntity.ok(service.update(id, documentType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 🔍 Search + pagination
    @GetMapping("/search")
    public ResponseEntity<Page<DocumentType>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String label,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(service.search(name, label, pageable));
    }
}
