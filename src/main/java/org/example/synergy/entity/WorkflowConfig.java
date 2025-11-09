package org.example.synergy.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "workflow_config")
public class WorkflowConfig extends BaseAuthorEntity {

    @Column(nullable = false)
    private String name; // Tên cấu hình

    @Column(nullable = false)
    private Long documentTypeId; // Loại văn bản

    private String description; // Mô tả

    // --- SLA Thẩm định ---
    private Integer maxSla;          // Tiêu chuẩn thời gian thẩm định (ngày)
    private Integer warningSla;      // Cảnh báo trước (ngày)
    private String warningPerson;    // Danh sách mã nhân viên cảnh báo, phân cách dấu phẩy

    @OneToMany(mappedBy = "workflowConfig", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // tránh vòng lặp khi serialize
    private List<WorkflowStep> steps;
}
