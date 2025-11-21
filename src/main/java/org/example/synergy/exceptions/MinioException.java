package org.example.synergy.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;

/**
 * The Class BusinessException.
 */
public class MinioException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(MinioException.class);

    private final String code;
    private final Serializable errorData;

    public MinioException() {
        this(null);
    }

    public MinioException(String code) {
        this(code, code);
    }

    public MinioException(String code, String message) {
        this(code, message, null);
    }

    public MinioException(String code, String message, Throwable cause) {
        this(code, message, null, cause);
    }

    public MinioException(String code, String message, Serializable errorData, Throwable cause) {
        super(message, cause);
        this.code = code;
        Serializable errorDataLocal = null;
        if (errorData != null) {
            errorDataLocal = errorData;
        } else if (message != null) {
            if (logger.isDebugEnabled()) {
                errorDataLocal = new HashMap<>(Collections.singletonMap("messsage", message));
            } else {
                errorDataLocal = new HashMap<>();
            }
        }
        this.errorData = errorDataLocal;
        logger.error(String.format("MinioException(code=[%s], message=[%s])", code, message), cause);
    }

    public String getCode() {
        return code;
    }

    public Serializable getErrorData() {
        return errorData;
    }
    
}
