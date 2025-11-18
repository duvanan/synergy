package org.example.synergy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "appraisal_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppraisalRequest extends BaseAuthorEntity{


    @Column(name = "request_code")
    private String requestCode;

    @Column(name = "document_type_id")
    private Long documentTypeId;

    @Column(name = "priority_level", length = 20)
    private String priorityLevel;

    @Column(name = "response_deadline")
    private LocalDate responseDeadline;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

}
