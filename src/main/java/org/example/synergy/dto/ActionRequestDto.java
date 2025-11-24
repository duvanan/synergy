package org.example.synergy.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActionRequestDto {
    private String actionBy;
    private String comment;
    private String fileName;
    private String filePath;

}
