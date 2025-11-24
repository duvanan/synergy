package org.example.synergy.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class PartnerDetailResponse {
    private Long id;
    private String type;
    private String name;
    private String partnerType;
    private String taxCode;
    private String invoiceAddress;
    private String invoiceEmail;
    private String legalRepresentativeName;
    private String legalRepresentativeId;
    private String legalRepresentativeAddress;
    private String legalRepresentativePhone;
    private String cccd;
    private String contactInfo;
    private Boolean connected;

    private List<PartnerRelationResponse> relatedPersons;
}

