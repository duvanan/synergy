package org.example.synergy.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAppraisalRequestDto {
    private Long appraisalRequestId; // null khi tạo mới, có giá trị khi cập nhật
    private Long documentTypeId;
    private String priorityLevel;
    private LocalDate responseDeadline;
    private String note;
    private String createdBy; // from UI / auth
    private String status; // DRAFT or SUBMITTED
    private List<FieldValueDto> dynamicFields;
    private List<FileDto> files;
    private List<AppraisalUser> appraisalUsers;
}
