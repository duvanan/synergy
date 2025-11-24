package org.example.synergy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StaffImageSignDTO {
    private Long staffImageSignId;
    private String path;
    private String name;
    private String fromDateActive;
    private String toDateActive;
    private Integer status;
    private String storage;
    private Long staffId;
    private Long staffIdVof2;
    private Long creatorIdVof2;
    private Integer type;
    private byte[] bytesRead;
}
