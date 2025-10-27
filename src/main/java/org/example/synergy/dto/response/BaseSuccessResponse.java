/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.dto.response;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;


@Data
public class BaseSuccessResponse implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 3863350524708498926L;
    
    @JsonProperty("code")
    private int httpStatusCode = HttpStatus.OK.value();
    
    @JsonProperty("message")
    private String message = HttpStatus.OK.getReasonPhrase();
}
