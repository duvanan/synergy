package org.example.synergy.controller;

import lombok.RequiredArgsConstructor;
import org.example.synergy.dto.request.WorkflowConfigRequest;
import org.example.synergy.entity.WorkflowConfig;
import org.example.synergy.service.WorkflowConfigService;
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
}
