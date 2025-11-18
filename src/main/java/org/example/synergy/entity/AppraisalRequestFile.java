package org.example.synergy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "appraisal_request_file")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppraisalRequestFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appraisal_request_id", nullable = false)
    private Long appraisalRequestId;

    @Column(name = "file_name", length = 255)
    private String fileName;

    @Column(name = "file_path", length = 500)
    private String filePath;

    @Column(name = "file_type", length = 50)
    private String fileType;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
}
