package org.example.synergy.controller;

import lombok.RequiredArgsConstructor;
import org.example.synergy.dto.request.NotificationConfigRequest;
import org.example.synergy.dto.response.NotificationConfigResponse;
import org.example.synergy.service.NotificationConfigService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification-configs")
@RequiredArgsConstructor
public class NotificationConfigController {

    private final NotificationConfigService service;

    @GetMapping
    public ResponseEntity<Page<NotificationConfigResponse>> search(
            @RequestParam(required = false) Long documentTypeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.search(documentTypeId, PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationConfigResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<NotificationConfigResponse> create(@RequestBody NotificationConfigRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationConfigResponse> update(
            @PathVariable Long id, @RequestBody NotificationConfigRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
