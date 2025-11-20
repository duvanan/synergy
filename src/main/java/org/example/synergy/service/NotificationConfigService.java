package org.example.synergy.service;

import org.example.synergy.dto.request.NotificationConfigRequest;
import org.example.synergy.dto.response.NotificationConfigResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationConfigService {

    Page<NotificationConfigResponse> search(Long documentTypeId, Pageable pageable);

    NotificationConfigResponse findById(Long id);

    NotificationConfigResponse create(NotificationConfigRequest request);

    NotificationConfigResponse update(Long id, NotificationConfigRequest request);

    void delete(Long id);
}
