package org.example.synergy.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.synergy.dto.request.NotificationConfigRequest;
import org.example.synergy.dto.response.NotificationConfigResponse;
import org.example.synergy.entity.DocumentType;
import org.example.synergy.entity.NotificationConfig;
import org.example.synergy.repository.DocumentTypeRepository;
import org.example.synergy.repository.NotificationConfigRepository;
import org.example.synergy.service.NotificationConfigService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class NotificationConfigServiceImpl implements NotificationConfigService {

    private final NotificationConfigRepository repository;
    private final DocumentTypeRepository documentTypeRepository;

    @Override
    public Page<NotificationConfigResponse> search(Long documentTypeId, Pageable pageable) {
        Page<NotificationConfig> configs;
        if (documentTypeId == null) {
            configs = repository.findAll(pageable);
        } else {
            configs = repository.findByDocumentTypeId(documentTypeId, pageable);
        }
        return configs.map(this::toResponse);
    }

    @Override
    public NotificationConfigResponse findById(Long id) {
        NotificationConfig config = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cấu hình thông báo"));
        return toResponse(config);
    }

    @Override
    public NotificationConfigResponse create(NotificationConfigRequest request) {
        NotificationConfig config = new NotificationConfig();
        config.setDocumentTypeId(request.getDocumentTypeId());
        config.setSlaContent(request.getSlaContent());
        config.setChannels(String.join(",", request.getChannels()));
        config.setTemplateHtml(request.getTemplateHtml());
        repository.save(config);
        return toResponse(config);
    }

    @Override
    public NotificationConfigResponse update(Long id, NotificationConfigRequest request) {
        NotificationConfig config = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cấu hình thông báo"));
        config.setDocumentTypeId(request.getDocumentTypeId());
        config.setSlaContent(request.getSlaContent());
        config.setChannels(String.join(",", request.getChannels()));
        config.setTemplateHtml(request.getTemplateHtml());
        repository.save(config);
        return toResponse(config);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private NotificationConfigResponse toResponse(NotificationConfig config) {
        String documentTypeName = documentTypeRepository.findById(config.getDocumentTypeId())
                .map(DocumentType::getDocumentTypeName)
                .orElse("");
        return NotificationConfigResponse.builder()
                .id(config.getId())
                .documentTypeName(documentTypeName)
                .slaContent(config.getSlaContent())
                .channels(Arrays.asList(config.getChannels().split(",")))
                .templateHtml(config.getTemplateHtml())
                .build();
    }
}
