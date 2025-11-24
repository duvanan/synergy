package org.example.synergy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDto {
    private String fileName;
    private String filePath;
    private String fileType;
}
