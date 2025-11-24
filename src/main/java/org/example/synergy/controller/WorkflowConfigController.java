package org.example.synergy.controller;

import lombok.RequiredArgsConstructor;
import org.example.synergy.dto.request.WorkflowConfigRequest;
import org.example.synergy.dto.response.WorkflowConfigResponse;
import org.example.synergy.entity.WorkflowConfig;
import org.example.synergy.service.WorkflowConfigService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workflows")
@RequiredArgsConstructor
public class WorkflowConfigController {

    private final WorkflowConfigService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody WorkflowConfigRequest req) {
        return ResponseEntity.ok(service.save(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody WorkflowConfigRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/steps")
    public ResponseEntity<?> getSteps(@PathVariable Long id) {
        return ResponseEntity.ok(service.findSteps(id));
    }

    // API filter + phân trang
    @GetMapping("/filter")
    public Page<WorkflowConfigResponse> filter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long documentTypeId,
            @RequestParam(required = false) Integer maxSla,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page == 0 ? page : page - 1, size);
        return service.filterWorkflowConfigs(name, documentTypeId, maxSla, pageable);
    }
}
