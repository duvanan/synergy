package org.example.synergy.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActionRequestDto {
    private String actionBy;
    private String comment;
}
