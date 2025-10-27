/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.exceptions;

import java.io.Serial;

import org.springframework.security.core.AuthenticationException;

/**
 * This exception is thrown in case of a not activated user trying to authenticate.
 */
public class UserNotActivatedException extends AuthenticationException {
    
    private String errorKey;
    
    @Serial
    private static final long serialVersionUID = 470025591172556734L;
    
    public UserNotActivatedException(String message) {
        super(message);
    }
    
    public UserNotActivatedException(String message, Throwable t) {
        super(message, t);
    }
    
    /**
     * Constructor
     *
     * @param message message
     * @param errorKey errorKey
     */
    public UserNotActivatedException(String message, String errorKey) {
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
    public UserNotActivatedException(String message, Throwable throwable, String errorKey) {
        super(message, throwable);
        this.errorKey = errorKey;
    }
}
