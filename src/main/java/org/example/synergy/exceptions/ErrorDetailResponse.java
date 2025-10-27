/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.exceptions;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * For ErrorResponse
 */
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class ErrorDetailResponse {
    
    @JsonProperty("timestamp")
    private long timestamp;
    
    @JsonProperty("path")
    private String path;
    
    @JsonProperty("error")
    private String error;
    
    @JsonProperty("error_message")
    private String errorMessage;
    
    @JsonProperty("error_key")
    private String errorKey;
}
