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
    @JsonBackReference // tránh vòng lặp khi serialize
    private WorkflowConfig workflowConfig;

    private Integer stepNumber; // Số bước (1, 2, 3,...)

    private String label;       // Tên hiển thị của bước

    private String stepType;    // Loại bước (Tất cả phê duyệt / Chỉ cần 1 người phê duyệt)

    private Double minValue;    // Giá trị nhỏ nhất (nếu có)
    private Double maxValue;    // Giá trị lớn nhất (nếu có)

    private String unit;        // Đơn vị (VD: %, ngày,...)
    private String tooltip;     // Gợi ý hướng dẫn người dùng

    // --- SLA riêng cho bước ---
    private Integer stepMaxSla;       // Tiêu chuẩn thời gian thẩm định (ngày)
    private Integer stepWarningSla;   // Cảnh báo trước (ngày)
    private String stepWarningPerson; // Danh sách nhân viên cảnh báo

    private String departmentId; // Mã phòng ban
    private String pic;          // Mã nhân viên phụ trách
}
