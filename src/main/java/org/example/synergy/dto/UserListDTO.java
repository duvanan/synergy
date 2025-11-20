/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 4061369222002779212L;
    
    @Schema(description = "User id", example = "1")
    @JsonProperty("id")
    private Long id;
    
    @Schema(description = "Full name", example = "Nguyễn Văn A")
    @JsonProperty("full_name")
    private String fullName;
    
    @Schema(description = "User name", example = "NguyenVanA")
    @JsonProperty("user_code")
    private String userCode;
    
    @Schema(description = "Email", example = "nva@gmail.com")
    @JsonProperty("email")
    private String email;
    
    @Schema(description = "Phone number", example = "123456789")
    @JsonProperty("phone_number")
    private String phoneNumber;
    
    @Schema(description = "Job Title ID", example = "1")
    @JsonProperty("job_title_id")
    private Long jobTitleId;
    
    @Schema(description = "Job Title name", example = "Trưởng phòng")
    @JsonProperty("job_title_name")
    private String jobTitleName;
    
    @Schema(description = "Department ID", example = "1")
    @JsonProperty("department_id")
    private Long departmentId;
    
    @Schema(description = "Department name", example = "Trung Tâm Phát Triển Phần Mềm")
    @JsonProperty("department_name")
    private String departmentName;
    
    @Schema(description = "Region code", example = "KV01")
    @JsonProperty("region_code")
    private String regionCode;
    
    @Schema(description = "Region name", example = "Khu vực 1")
    @JsonProperty("region_name")
    private String regionName;
    
    @Schema(description = "Region fullname", example = "Trung tâm Tần số Vô tuyến điện Khu vực I")
    @JsonProperty("region_fullname")
    private String regionFullname;
    
    @Schema(description = "User type", example = "Tài khoản admin")
    @JsonProperty("type")
    private Integer type;
    
    @Schema(description = "User status", example = "true")
    @JsonProperty("status")
    private Boolean status;
}
