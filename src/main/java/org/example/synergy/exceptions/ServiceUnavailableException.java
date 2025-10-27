/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Http exception return 503
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ServiceUnavailableException extends RuntimeException {
    
    private String errorKey;
    
    /**
     * Constructor
     *
     * @param message message
     */
    public ServiceUnavailableException(String message) {
        super(message);
    }
    
    /**
     * Constructor
     *
     * @param message   message
     * @param throwable throwable
     */
    public ServiceUnavailableException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
    /**
     * Constructor
     *
     * @param message message
     * @param errorKey errorKey
     */
    public ServiceUnavailableException(String message, String errorKey) {
        super(message);
        this.errorKey = errorKey;
    }
    
    /**
     * Constructor
     *
     * @param message message
     * @param throwable throwable
     * @param errorKey errorKey
     */
    public ServiceUnavailableException(String message, Throwable throwable, String errorKey) {
        super(message, throwable);
        this.errorKey = errorKey;
    }
}
