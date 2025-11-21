/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.dto.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.synergy.dto.request.BaseSearch;
import org.example.synergy.jackson.BooleanDeserializer;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserSearch extends BaseSearch implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 8660658698886967605L;
    
    @Schema(description = "department ids", example = "[1,2,3]")
    @JsonProperty("department_ids")
    private List<Long> departmentIds;
    
    @Schema(description = "User types", example = "[1,2,3]")
    @JsonProperty("user_types")
    private List<Integer> userTypes;
    
    @JsonProperty("region_codes")
    private List<String> regionCodes;
    
    @Schema(description = "status", example = "true")
    @JsonProperty("status")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean status;
}
