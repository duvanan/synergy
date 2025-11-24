package org.example.synergy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VofficeFileDto {
    @JsonProperty("fileAttachmentId")
    private Long fileAttachmentId;
    @JsonProperty("fileName")
    private String fileName;
    @JsonProperty("filePath")
    private String filePath;
    @JsonProperty("storage")
    private String storage;
    @JsonProperty("lFilePage")
    private Integer lFilePage;
    @JsonProperty("lFileSize")
    private Long lFileSize;
    @JsonProperty("fileOrder")
    private Long fileOrder;
    @JsonProperty("canRead")
    private Integer canRead;
    @JsonProperty("isCopy")
    private Boolean isCopy;
    @JsonProperty("hasFileEditHistories")
    private Boolean hasFileEditHistories;
    @JsonProperty("type")
    private Integer type;
}
