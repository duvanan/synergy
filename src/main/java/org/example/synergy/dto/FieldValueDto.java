package org.example.synergy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldValueDto {
    private String fieldKey;
    private String fieldLabel;
    private String fieldType;
    private String fieldValue;
}
