package org.example.synergy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
    private Long employeeId;
    private String orgName;
    private Long sysOrganizationId;
    private String pathName;
    private String displayName;
    private String position;
    private String employeeCode;
    private String mobilePhone;
    private String email;
}
