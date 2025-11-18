package org.example.synergy.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppraisalRequestDetailDto {
    private Long id;
    private String requestCode;
    private Long documentTypeCode;
    private String priorityLevel;
    private LocalDate responseDeadline;
    private String note;
    private String status;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<FieldValueDto> dynamicFields;
    private List<FileDto> files;
}
