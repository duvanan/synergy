package org.example.synergy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "notification_config")
@Data
public class NotificationConfig extends BaseAuthorEntity{

    @Column(name = "document_type_id", nullable = false)
    private Long documentTypeId;

    @Column(name = "sla_content", length = 1000, nullable = false)
    private String slaContent;

    @Column(name = "channels", length = 255, nullable = false)
    private String channels; // ví dụ: "EMAIL,WEB"

    @Column(name = "template_html", columnDefinition = "TEXT", nullable = false)
    private String templateHtml;

}
