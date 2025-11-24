package org.example.synergy.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "workflow_step")
public class WorkflowStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_config_id")
    @JsonBackReference
    private WorkflowConfig workflowConfig;

    private Integer parentStep;    // Bước cha: 1, 2, 3...
    private Integer childStep;     // Bước con: 1, 2, 3...

    private Long departmentId;   // Phòng ban thẩm định
    private String pic;            // Nhân sự thẩm định

    private Boolean isLeadUnit;    // Đơn vị chủ trì?

    // SLA
    private Integer stepMaxSla;        // Giới hạn thời gian
    private Integer stepWarningSla;    // Cảnh báo trước
    private String stepWarningPerson;  // Người nhận cảnh báo

    private String tooltip;        // Gợi ý
}
