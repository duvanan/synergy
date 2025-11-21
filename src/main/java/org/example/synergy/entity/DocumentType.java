package org.example.synergy.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "document_type")
public class DocumentType extends BaseAuthorEntity{


    /** Tên loại văn bản */
    private String name;

    /** Mã loại văn bản */
    private String code;

    /** Mô tả loại văn bản */
    private String description;

    /** Đường dẫn file biểu mẫu lưu trên MinIO hoặc local */
    private String templateFilePath;


    /** Đường dẫn file biểu mẫu lưu trên MinIO hoặc local */
    private String fileName;

    @OneToMany(mappedBy = "documentType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentTypeAttribute> attributes;
}
