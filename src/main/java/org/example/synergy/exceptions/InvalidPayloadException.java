/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Exception thrown if state of payload instance is invalid.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuppressWarnings("serial")
public class InvalidPayloadException extends RuntimeException {
    
    private Object errorBody;
    
    /**
     * Create instance with detailed message.
     *
     * @param message the detailed message
     */
    public InvalidPayloadException(String message) {
        super(message);
    }
    
    /**
     * constructor.
     *
     * @param message   Message
     * @param errorBody errorBody
     */
    public InvalidPayloadException(String message, Object errorBody) {
        super(message);
        this.errorBody = errorBody;
    }
    
    /**
     * constructor.
     *
     * @param message   Message
     * @param exception Exception Object
     */
    public InvalidPayloadException(String message, Exception exception) {
        super(message, exception);
    }
    
    /**
     * constructor.
     *
     * @param message   Message
     * @param errorBody errorBody
     * @param exception Exception Object
     */
    public InvalidPayloadException(String message, Object errorBody, Exception exception) {
        super(message, exception);
        this.errorBody = errorBody;
    }
}
