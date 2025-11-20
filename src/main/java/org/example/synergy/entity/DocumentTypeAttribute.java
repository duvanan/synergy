package org.example.synergy.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "document_type_attribute")
public class DocumentTypeAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** FK đến loại văn bản */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id")
    private DocumentType documentType;

    /** Label hiển thị thuộc tính */
    private String label;

    /** Mã thuộc tính trong hệ thống */
    private String fieldCode;

    /** Có bắt buộc nhập hay không */
    private Boolean required;

    /** Kiểu dữ liệu (string, number, date, boolean, v.v.) */
    private String dataType;

    /** Giá trị mặc định */
    private String defaultValue;

    /** Giá trị nhỏ nhất */
    private String minValue;

    /** Giá trị lớn nhất */
    private String maxValue;

    /** Danh sách đơn vị có thể dùng, cách nhau dấu ; */
    private String unitList;

    /** Tooltip hướng dẫn */
    private String tooltip;

    /** Danh sách giá trị lựa chọn */
    private String valueList;
}
