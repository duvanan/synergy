package org.example.synergy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentTreeDTO {

    private Long id;
    private String code;
    private String name;
    private String regionCode;
    private String regionName;
    private String regionFullname;
    private String description;
    private String organizationName;
    private String organizationCode;
    private Integer isDeleted;
    private Integer isActive;
    private String parentCode;

    private List<DepartmentTreeDTO> children = new ArrayList<>();
}
