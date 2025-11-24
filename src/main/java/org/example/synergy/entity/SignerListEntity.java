package org.example.synergy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "signer_list")
@Setter
@Getter
public class SignerListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Khóa chính

    @Column(name = "signature_detail_id")
    private Long signatureDetailId; // Khóa ngoại đến bảng program_signature_detail

    @Column(name = "staff_id")
    private Long staffId; // ID nhân viên ký

    @Column(name = "staff_name", length = 255)
    private String staffName; // Tên nhân viên

    @Column(name = "email", length = 255)
    private String email; // Email

    @Column(name = "organization_id")
    private Long organizationId; // ID tổ chức

    @Column(name = "organization_name", length = 255)
    private String organizationName; // Tên tổ chức

    @Column(name = "sign_image_id", length = 100)
    private Long signImageId; // ID ảnh chữ ký (từ API getImageSign)

    @Column(name = "name_image", length = 255)
    private String nameImage; // Tên ảnh ký

    @Column(name = "sign_level")
    private Integer signLevel; // Thứ tự ký (bắt đầu từ 0)

    @Column(name = "show_image")
    private Integer showImage; // Có hiển thị ảnh chữ ký không (tinyint)

    @Column(name = "promulgating_signer")
    private Boolean promulgatingSigner; // Có phải người ban hành không

    @Column(name = "parallelsign_sign_group", length = 100)
    private Integer parallelsignSignGroup; // Nhóm ký song song

    @Column(name = "path_image")
    private String pathImage;

    @Column(name = "bucket_name")
    private String bucketName;
}
