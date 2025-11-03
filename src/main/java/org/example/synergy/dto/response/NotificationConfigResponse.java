package org.example.synergy.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class NotificationConfigResponse {
    private Long id;
    private String documentTypeName;
    private String slaContent;
    private List<String> channels;
    private String templateHtml;
    private String createdBy;
    private LocalDateTime createdDate;
}
