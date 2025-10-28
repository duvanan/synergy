package org.example.synergy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "`user`")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "`code`", length = 30)
    private String code;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "failed_password_attempts")
    private Integer failedPasswordAttempts = 0;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    @Column(name = "job_title_id")
    private Long jobTitleId;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "failed_otp_attempts")
    private Integer failedOtpAttempts = 0;

    @Column(name = "jti", length = 100, unique = true)
    private String jti;

    @Column(name = "`type`", nullable = false)
    private Integer type;

}
