package org.example.synergy.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class NotificationConfigRequest {
    private Long documentTypeId;
    private String slaContent;
    private List<String> channels; // ["EMAIL", "WEB", "SMS"]
    private String templateHtml;
}
