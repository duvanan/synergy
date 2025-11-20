/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicRoleDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 4910889616867808391L;
    
    @Schema(description = "id", example = "1")
    @JsonProperty("id")
    private Long id;
    
    @Schema(description = "name", example = "Giao việc")
    @JsonProperty("name")
    private String name;
}
