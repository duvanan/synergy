package org.example.synergy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "appraisal_request_field_value")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppraisalRequestFieldValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appraisal_request_id", nullable = false)
    private Long appraisalRequestId;

    @Column(name = "field_key", nullable = false, length = 255)
    private String fieldKey;

    @Column(name = "field_label", length = 255)
    private String fieldLabel;

    @Column(name = "field_type", length = 50)
    private String fieldType;

    @Column(name = "field_value", columnDefinition = "TEXT")
    private String fieldValue;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
