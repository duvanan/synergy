package org.example.synergy.dto.response;

import lombok.*;
import org.example.synergy.entity.DocumentSignEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentSignResponse {


    private Long id;

    private Long signatureDetailId;

    private String name;

    private String path;
    private String bucketName;

    private Integer documentType;

    public static DocumentSignResponse fromEntity(DocumentSignEntity entity) {
        return DocumentSignResponse.builder()
                .id(entity.getId())
                .signatureDetailId(entity.getSignatureDetailId())
                .name(entity.getName())
                .path(entity.getPath())
                .bucketName(entity.getBucketName())
                .documentType(entity.getDocumentType())
                .build();
    }
}
