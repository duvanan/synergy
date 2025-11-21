package org.example.synergy.dto.request.voffice;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddTextRequest {

    @Size(max = 50, message = "Mã văn bản không được vượt quá 50 ký tự")
    private String documentCode; //Ký hiệu văn bản
    @Size(max = 250, message = "Tên văn bản không được vượt quá 250 ký tự")
    private String documentName; //Tên văn bản
    @Size(max = 500, message = "Nội dung văn bản không được vượt quá 500 ký tự")
    private String documentContent;
    private Long areaId; //Ngành liên quan
    private Long typeId; //Hình thức văn bản
    private Long stypeId; //Độ mật văn bản
    private Long priorityId; //Độ khẩn văn bản
    private String areaName; //Ngành liên quan
    private String typeName; //Hình thức văn bản
    private String stypeName; //Độ mật văn bản
    private String priorityName; //Độ khẩn văn bản
    @Size(max = 250, message = "Nơi nhận không được vượt quá 250 ký tự")
    private String receivingPlace; //Nơi nhận
    private Integer autoPromulgateText; //Nội dung ban hành tự động
    private Long promulgatingSignerDepartmentId; //ID đơn vị ban hành
    private String userName;
    private String password;
    private List<StaffDTO> lstStaff;
    private List<MultipartFile> lstFileSign;
    private List<MultipartFile> listFileSignOther;
    private List<MultipartFile> lstFileEvidence;
    private List<MultipartFile> lstFileDocument;
}
