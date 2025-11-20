/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.model;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.synergy.dto.UserListDTO;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "`user`")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SqlResultSetMapping(name = "GetUserList", classes = {
    @ConstructorResult(targetClass = UserListDTO.class, columns = {
        @ColumnResult(name = "id", type = Long.class),
        @ColumnResult(name = "full_name", type = String.class),
        @ColumnResult(name = "userCode", type = String.class),
        @ColumnResult(name = "email", type = String.class),
        @ColumnResult(name = "phone_number", type = String.class),
        @ColumnResult(name = "job_title_id", type = Long.class),
        @ColumnResult(name = "job_title_name", type = String.class),
        @ColumnResult(name = "department_id", type = Long.class),
        @ColumnResult(name = "department_name", type = String.class),
        @ColumnResult(name = "region_code", type = String.class),
        @ColumnResult(name = "region_name", type = String.class),
        @ColumnResult(name = "region_fullname", type = String.class),
        @ColumnResult(name = "type", type = Integer.class),
        @ColumnResult(name = "status", type = Boolean.class)
    })
})
public class User extends AbstractAuditingEntity implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 787005999925410388L;
    
    @Id
    @Column(name = "id", unique = true, updatable = false)
    private Long id;
    
    @Column(name = "`code`", length = 30)
    private String code;
    
    @Column(name = "full_name")
    private String fullName;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "failed_password_attempts")
    private Integer failedPasswordAttempts = 0;
    
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    
    @Column(name = "job_title_id")
    private Long jobTitleId;
    
    @Column(name = "department_id")
    private Long departmentId;
    
    @Column(name = "otp_expired")
    private Instant otpExpired;
    
    @Column(name = "failed_otp_attempts")
    private Integer failedOtpAttempts = 0;
    
    @Column(name = "jti", length = 100, unique = true)
    private String jti;
    
    @Column(name = "status")
    private Boolean status;
    
    @Column(name = "is_assign")
    private Boolean isAssign;
    
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    
    @Column(name = "`type`")
    private Integer type;

    @Column(name = "user_code")
    private String userCode;

    @Column(name = "gender")
    private String gender;

    @Column(name = "organization_code")
    private String organizationCode;

    @Column(name = "staff_role")
    private String staffRole;

    @Column(name = "staff_level")
    private String staffLevel;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "user_key")
    private String userKey;

    @Column(name = "directory_id")
    private Integer directoryId;

    @Column(name = "jira_active")
    private Integer jiraActive;

    @Column(name = "staff_position")
    private String staffPosition;

    @Column(name = "organization_id")
    private Integer organizationId;

    @Column(name = "position_id")
    private Integer positionId;
}
