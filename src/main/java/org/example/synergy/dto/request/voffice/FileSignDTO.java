package org.example.synergy.dto.request.voffice;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileSignDTO {
    private String name;
    private String fileOrder;
    private String filePath;
}
