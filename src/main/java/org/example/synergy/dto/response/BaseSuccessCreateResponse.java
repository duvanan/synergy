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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseSuccessCreateResponse implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 7543055871129345905L;
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("code")
    private int httpStatusCode = HttpStatus.CREATED.value();
    
    @JsonProperty("message")
    private String message = HttpStatus.CREATED.getReasonPhrase();
    
    public BaseSuccessCreateResponse(Long id) {
        this.id = id;
    }
}
