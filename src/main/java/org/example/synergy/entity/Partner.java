package org.example.synergy.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "partner")
public class Partner extends BaseAuthorEntity {

    // Phân loại
    @Column(nullable = false)
    private String type; // BUSINESS hoặc PERSONAL

    private String name;
    private String partnerType;

    // Dành cho tổ chức
    private String taxCode;
    private String invoiceAddress;
    private String invoiceEmail;
    private String legalRepresentativeName;
    private String legalRepresentativeId;
    private String legalRepresentativeAddress;
    private String legalRepresentativePhone;

    // Dành cho cá nhân
    private String cccd;
    private String contactInfo;

    // Chung
    private Boolean connected;
}
