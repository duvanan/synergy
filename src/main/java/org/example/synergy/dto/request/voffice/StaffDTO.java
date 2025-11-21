package org.example.synergy.dto.request.voffice;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StaffDTO {

    private Long staffId; //ID nhân viên ký
    private String staffName; //Tên nhân viên
    private String email; //email
    private String nameImage; //Ten anh ky
    private Long organizationId; //ID tổ chức
    private String organizationName; //Tên tổ chức
    private Long signImageId; //ID ảnh chữ ký từ API getImagesign
    private Integer signLevel; //Thứ tự ký (bắt đầu từ 0)
    private Integer showImage; //Có hiển thị ảnh chữ ký không
    private Boolean promulgatingSigner; //Có phải người ban hành không
    private Integer parallelSignGroup; //Nhóm ký song song
    private MultipartFile fileImage;
}
