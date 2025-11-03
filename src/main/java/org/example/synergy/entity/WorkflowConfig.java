package org.example.synergy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "workflow_config")
public class WorkflowConfig extends BaseAuthorEntity{

    private Long documentTypeId; // idLoaiVanBan
    private Integer maxSla;
    private Integer warningSla;
    private String warningPerson; // list mã nhân viên cảnh báo (ngăn cách bởi dấu ,)
    private String description;
}
