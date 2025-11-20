/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.synergy.jackson.TrimDeserializer;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseSearch implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 4751566805297361587L;
    
    @Schema(description = "search keyword", example = "keyword")
    @JsonDeserialize(using = TrimDeserializer.class)
    @JsonProperty("search_keyword")
    private String keyword;
    
    @Schema(description = "Number page", minimum = "1", defaultValue = "1", example = "1", nullable = true)
    @Min(1)
    @JsonProperty("page_number")
    private Integer pageNumber = 1;
    
    @Schema(description = "Number of records per page", minimum = "1", defaultValue = "20", example = "20", nullable = true)
    @Min(10)
    @Max(100)
    @JsonProperty("page_size")
    private Integer pageSize = 10;
}
