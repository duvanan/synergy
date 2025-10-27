/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.exceptions;

import java.net.URI;

public final class ErrorConstants {
    
    private ErrorConstants() {
    }
    
    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    
    public static final String ERR_VALIDATION = "error.validation";
    
    public static final String PROBLEM_BASE_URL = "https://www.jhipster.tech/problem";
    
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
    
    static final String TIMESTAMP_KEY = "timestamp";
    
    public static final String PATH_KEY = "path";
    
    static final String ERROR_KEY = "error";
    
    static final String ERROR_MESSAGE_KEY = "error_message";
    
    static final String ERROR_KEY_KEY = "error_key";
    
    public static final String MESSAGE_KEY = "message";
    
    public static final String FIELD_ERRORS_KEY = "fieldErrors";
    
    public static final boolean CASUAL_CHAIN_ENABLED = false;
}
