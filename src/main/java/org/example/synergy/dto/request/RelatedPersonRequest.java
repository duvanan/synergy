package org.example.synergy.dto.request;

import lombok.Data;

@Data
public class RelatedPersonRequest {
    private String id;              // ID bản ghi (nếu sửa)
    private String employeeCode;    // Mã nhân viên
    private String employeeName;    // Tên nhân viên
    private String relationship;    // Mối quan hệ
}
