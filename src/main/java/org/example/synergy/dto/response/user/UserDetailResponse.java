/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.synergy.dto.BasicRoleDTO;
import org.example.synergy.dto.response.BaseSuccessResponse;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailResponse extends BaseSuccessResponse implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1207731840566359226L;
    
    @Schema(description = "User id", example = "1")
    @JsonProperty("id")
    private Long id;
    
    @Schema(description = "Full name", example = "Nguyễn Văn A")
    @JsonProperty("full_name")
    private String fullName;
    
    @Schema(description = "User name", example = "NguyenVanA")
    @JsonProperty("username")
    private String username;
    
    @Schema(description = "Email", example = "nva@gmail.com")
    @JsonProperty("email")
    private String email;
    
    @Schema(description = "Phone number", example = "123456789")
    @JsonProperty("phone_number")
    private String phoneNumber;
    
    @Schema(description = "Job Title ID", example = "1")
    @JsonProperty("job_title_id")
    private Long jobTitleId;
    
    @Schema(description = "Job title name", example = "Trưởng phòng")
    @JsonProperty("job_title_name")
    private String jobTitleName;
    
    @Schema(description = "Department ID", example = "1")
    @JsonProperty("department_id")
    private Long departmentId;
    
    @Schema(description = "Department name", example = "Trung tâm phát triển phần mềm")
    @JsonProperty("department_name")
    private String departmentName;
    
    @Schema(description = "User type", example = "1")
    @JsonProperty("type")
    private Integer type;
    
    @Schema(description = "User status", example = "true")
    @JsonProperty("status")
    private Boolean status;
    
    @Schema(description = "is assign", example = "true")
    @JsonProperty("is_assign")
    private Boolean isAssign;
    
    @JsonProperty("roles")
    private List<BasicRoleDTO> roles;
    
//    @JsonProperty("file")
//    private FileStorageDTO file;
//
//    public UserDetailResponse(BaseUserDTO user) {
//        this.id = user.getId();
//        this.fullName = user.getFullName();
//        this.username = user.getUsername();
//        this.email = user.getEmail();
//        this.phoneNumber = user.getPhoneNumber();
//        this.jobTitleId = user.getJobTitleId();
//        this.jobTitleName = user.getJobTitleName();
//        this.departmentId = user.getDepartmentId();
//        this.departmentName = user.getDepartmentName();
//        this.type = user.getType();
//        this.status = user.getStatus();
//        this.isAssign = user.getIsAssign();
//        this.roles = user.getRoles();
//        this.file = user.getFile();
//    }
}
