package org.example.synergy.entity;

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

    private Long workflowConfigId;

    private Integer stepNumber;
    private Integer subStepNumber;

    private String departmentId; // PB01
    private String pic;          // mã nhân viên phụ trách

    private Integer stepMaxSla;
    private Integer stepWarningSla;

    private String stepWarningPerson; // list nhân viên cảnh báo, phân cách dấu phẩy
}
