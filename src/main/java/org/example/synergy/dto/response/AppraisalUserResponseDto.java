package org.example.synergy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppraisalUserResponseDto {
    private Long id;
    private Long appraisalRequestId;
    private String name;
    private String code;
    private Double level;
    private String organization;
    private String role;
    private Boolean appraised;
    private String filePath;
    private String fileName;
    private String note;
    private Boolean hostUnit;
    private LocalDateTime appraisedTime;
}
