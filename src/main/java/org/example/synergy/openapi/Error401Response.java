/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.openapi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * For ErrorResponse of Swagger Document.
 */
@Getter
@ToString
@EqualsAndHashCode
@Schema(name = "Error401Response", description = "Error 401 response model.")
public class Error401Response {
    
    @Schema(description = "Error occurrence timestamp(Epoch milliseconds)", example = "1678434125918")
    private long timestamp;
    
    @Schema(description = "The requested path", example = "/api/dummy")
    private String path;
    
    @Schema(description = "Error Type", example = "unauthorized")
    private String error;
    
    @Schema(name = "error_message", description = "Error Message", example = "Full authentication is required to access this resource")
    private String errorMessage;
    
    @Schema(name = "error_key", description = "Error Key", example = "dump_error_key")
    private String errorKey;
}
