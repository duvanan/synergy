package org.example.synergy.dto.request;

import lombok.Data;

@Data
public class VOfficeFileRequest {
    private String fileName;
    private String storage;
    private String filePath;
    private String userName;
    private String password;
}
