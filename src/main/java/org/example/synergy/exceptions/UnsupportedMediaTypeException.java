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
 * Http exception return 415
 */
@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class UnsupportedMediaTypeException extends RuntimeException {
    
    private String errorKey;
    
    /**
     * Constructor
     *
     * @param message message
     */
    public UnsupportedMediaTypeException(String message) {
        super(message);
    }
    
    /**
     * Constructor
     *
     * @param message   message
     * @param throwable throwable
     */
    public UnsupportedMediaTypeException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
    /**
     * Constructor
     *
     * @param message message
     * @param errorKey errorKey
     */
    public UnsupportedMediaTypeException(String message, String errorKey) {
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
    public UnsupportedMediaTypeException(String message, Throwable throwable, String errorKey) {
        super(message, throwable);
        this.errorKey = errorKey;
    }
}
