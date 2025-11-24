package org.example.synergy.dto.request.voffice;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    String employeeId;
    String employeeCode;
    String displayName;
    String email;
    String mobilePhone;
    String sysOrganizationId;
    String position;
    String pathName;
    String orgName;
}
