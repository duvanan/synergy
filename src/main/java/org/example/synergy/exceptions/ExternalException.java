/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.exceptions;

import lombok.Getter;

import org.example.synergy.external.ExternalErrorResponse;
import org.springframework.http.HttpStatus;

import org.apache.commons.lang3.StringUtils;


@Getter
public class ExternalException extends RuntimeException {
    
    private final HttpStatus httpStatus;
    
    private final ExternalErrorResponse errorResponse;
    
    public ExternalException(HttpStatus httpStatus, ExternalErrorResponse errorResponse) {
        super(StringUtils.isNotBlank(errorResponse.getMessage()) ? errorResponse.getMessage() : "");
        this.httpStatus = httpStatus;
        this.errorResponse = errorResponse;
    }
}
