package org.example.synergy.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.synergy.entity.SignerListEntity;

@Getter
@Setter
public class SignerListResponse {

    private Long id; // Khóa chính

    private Long signatureDetailId; // Khóa ngoại đến bảng program_signature_detail

    private Long staffId; // ID nhân viên ký

    private String staffName; // Tên nhân viên

    private String email; // Tên nhân viên

    private Long organizationId; // ID tổ chức

    private String organizationName; // Tên tổ chức

    private Long signImageId; // ID ảnh chữ ký (từ API getImageSign)

    private String nameImage; // Tên ảnh chữ ký (từ API getImageSign)

    private Integer signLevel; // Thứ tự ký (bắt đầu từ 0)

    private Integer showImage; // Có hiển thị ảnh chữ ký không (tinyint)

    private Boolean promulgatingSigner; // Có phải người ban hành không

    private Integer parallelsignSignGroup; // Nhóm ký song song

    private String pathImage;

    private String bucketName;

    public static SignerListResponse fromEntity(SignerListEntity entity) {
        SignerListResponse response = new SignerListResponse();
        response.setId(entity.getId());
        response.setSignatureDetailId(entity.getSignatureDetailId());
        response.setStaffId(entity.getStaffId());
        response.setStaffName(entity.getStaffName());
        response.setEmail(entity.getEmail());
        response.setOrganizationId(entity.getOrganizationId());
        response.setOrganizationName(entity.getOrganizationName());
        response.setSignImageId(entity.getSignImageId());
        response.setNameImage(entity.getNameImage());
        response.setSignLevel(entity.getSignLevel());
        response.setShowImage(entity.getShowImage());
        response.setPromulgatingSigner(entity.getPromulgatingSigner());
        response.setParallelsignSignGroup(entity.getParallelsignSignGroup());
        response.setPathImage(entity.getPathImage());
        response.setBucketName(entity.getBucketName());
        return response;
    }

}
