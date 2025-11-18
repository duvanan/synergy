package org.example.synergy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "appraisal_request_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppraisalRequestHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appraisal_request_id", nullable = false)
    private Long appraisalRequestId;

    @Column(name = "action", length = 50)
    private String action;

    @Column(name = "action_by", length = 100)
    private String actionBy;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
