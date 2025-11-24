package org.example.synergy.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VofficeUploadFileResponse {
    private String filePath;
    private String fileName;
    private String fileType;
}
