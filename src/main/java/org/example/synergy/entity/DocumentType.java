package org.example.synergy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "document_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentType extends BaseAuthorEntity{

    // Thông tin chung
    @Column(name = "document_type_name", nullable = false, length = 255)
    private String documentTypeName;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Thuộc tính
    @Column(length = 255)
    private String label;

    @Column(name = "is_required")
    private Boolean isRequired;

    @Column(name = "attribute_description", columnDefinition = "TEXT")
    private String attributeDescription;

    @Column(name = "data_type", length = 100)
    private String dataType;

    @Column(name = "default_value", length = 500)
    private String defaultValue;

    @Column(name = "min_value", length = 100)
    private String minValue;

    @Column(name = "max_value", length = 100)
    private String maxValue;

    @Column(name = "value_list", columnDefinition = "TEXT")
    private String valueList;

    @Column(name = "currency_units", length = 255)
    private String currencyUnits;

    @Column(length = 500)
    private String tooltip;

    // File
    @Column(name = "template_file_url", length = 500)
    private String templateFileUrl;

}
