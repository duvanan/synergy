package org.example.synergy.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "partner_relation")
public class PartnerRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "partner_id", nullable = false)
    private Long partnerId;

    @Column(name = "employee_code")
    private String employeeCode;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "relationship")
    private String relationship;
}
