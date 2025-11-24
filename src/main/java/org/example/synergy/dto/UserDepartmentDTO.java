package org.example.synergy.dto;

public interface UserDepartmentDTO {

    Long getUserId();
    String getCode();
    String getFullName();
    String getEmail();
    String getPassword();
    Integer getFailedPasswordAttempts();
    String getPhoneNumber();
    Long getDepartmentId();
    String getJti();
    Boolean getStatus();
    Boolean getIsAssign();
    Boolean getIsDeleted();
    Integer getType();
    String getUserCode();
    String getGender();
    String getOrganizationCode();
    String getStaffRole();
    String getStaffLevel();
    Integer getIsActive();
    String getUserKey();
    Integer getDirectoryId();
    Integer getJiraActive();
    String getStaffPosition();
    Integer getOrganizationId();
    Integer getPositionId();

    // DEPARTMENT
    Long getDeptId();
    String getDeptCode();
    String getDeptName();
    String getRegionCode();
    String getRegionName();
    String getRegionFullname();
    String getDescription();
    String getOrganizationName();
    String getDeptOrganizationCode();
    Integer getDeptIsDeleted();
    Integer getDeptIsActive();
    String getParentCode();
}
