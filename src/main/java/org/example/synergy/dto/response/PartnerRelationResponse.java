package org.example.synergy.dto.response;

import lombok.Data;

@Data
public class PartnerRelationResponse {
    private Long id;
    private String employeeName;
    private String employeeCode;
    private String relationship;
}
