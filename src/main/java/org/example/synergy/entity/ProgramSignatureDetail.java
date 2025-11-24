package org.example.synergy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "program_signature_detail")
public class ProgramSignatureDetail extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "program_plan_version_id")
    private Long programPlanVersionId;

    @Size(max = 255)
    @Column(name = "document_code")
    private String documentCode;

    @Size(max = 255)
    @Column(name = "document_name")
    private String documentName;

    @Lob
    @Column(name = "document_content")
    private String documentContent;

    @Column(name = "area_id")
    private Long areaId;

    @Column(name = "type_id")
    private Long typeId;

    @Column(name = "stype_id")
    private Long stypeId;

    @Column(name = "priority_id")
    private Long priorityId;

    @Column(name = "area_name")
    private String areaName;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "stype_name")
    private String stypeName;

    @Column(name = "priority_name")
    private String priorityName;

    @Size(max = 255)
    @Column(name = "receiving_place")
    private String receivingPlace;

    @Column(name = "auto_promulgate_text")
    private Integer autoPromulgateText;

    @Column(name = "status")
    private String status;

    @Column(name = "sign_date")
    private Date signDate;

    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "trans_code")
    private String transCode;

}