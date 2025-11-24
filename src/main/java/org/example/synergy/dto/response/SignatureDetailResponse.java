package org.example.synergy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignatureDetailResponse {

    private Long id;

    private Long programPlanVersionId;

    private String programPlanName;

    private String programName;

    private Long programType;

    private String programTypeName;

    private String documentCode;

    private String documentName;

    private String status;

    private String statusName;

    private String createdBy;

    private String createdName;

    private List<SignerListResponse> signerListResponses;

    private List<DocumentSignResponse> documentSignResponses;

    private Date signDate;

    private Integer documentNumber;

    private String areaName; //Ngành liên quan

    private String typeName; //Hình thức văn bản

    private String stypeName; //Độ mật văn bản

    private String priorityName; //Độ khẩn văn bản

    private String documentContent;

    private String receivingPlace;

    private Integer autoPromulgateText;

    public SignatureDetailResponse(Long id, Long programPlanVersionId, String programPlanName, String documentCode, String documentName,
                                   String status, String statusName, String createdBy, String createdName, Date signDate,
                                   String areaName, String typeName, String stypeName, String priorityName, String documentContent,
                                   String receivingPlace, Integer autoPromulgateText, String programName, Long programType
    ) {
        this.id = id;
        this.programPlanVersionId = programPlanVersionId;
        this.programPlanName = programPlanName;
        this.programName = programName;
        this.programType = programType;
        this.documentCode = documentCode;
        this.documentName = documentName;
        this.status = status;
        this.statusName = statusName;
        this.createdBy = createdBy;
        this.createdName = createdName;
        this.signDate = signDate;
        this.areaName = areaName;
        this.typeName = typeName;
        this.stypeName = stypeName;
        this.priorityName = priorityName;
        this.documentContent = documentContent;
        this.receivingPlace = receivingPlace;
        this.autoPromulgateText = autoPromulgateText;
    }


}
