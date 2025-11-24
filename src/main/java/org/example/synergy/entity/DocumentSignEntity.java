package org.example.synergy.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "document_sign")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentSignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "signature_detail_id")
    private Long signatureDetailId;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "path", length = 500)
    private String path;

    @Column(name = "bucket_name", length = 500)
    private String bucketName;

    @Column(name = "document_type", length = 50)
    private Integer documentType;
}
