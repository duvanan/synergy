package org.example.synergy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appraisal_user")
public class AppraisalUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appraisal_request_id")
    private Long appraisalRequestId;

    private String name;

    private String code;

    private Integer level;

    private String organization;

    private String role;

    private Boolean appraised;

    @Column(name = "host_unit")
    private String hostUnit;

}
