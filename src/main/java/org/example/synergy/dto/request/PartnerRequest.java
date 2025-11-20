package org.example.synergy.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class PartnerRequest {
    private Long id;
    private String type; // BUSINESS hoặc PERSONAL

    private String name;
    private String partnerType;

    // Tổ chức
    private String taxCode;
    private String invoiceAddress;
    private String invoiceEmail;
    private String legalRepresentativeName;
    private String legalRepresentativeId;
    private String legalRepresentativeAddress;
    private String legalRepresentativePhone;

    // Cá nhân
    private String cccd;
    private String contactInfo;

    // Chung
    private Boolean connected;
    private List<RelatedPersonRequest> relatedPersons;
}
