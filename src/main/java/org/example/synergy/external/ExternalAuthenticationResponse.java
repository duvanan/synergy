/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.external;

import java.io.Serial;
import java.io.Serializable;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.synergy.dto.response.BaseSuccessResponse;
import org.example.synergy.dto.response.external.ExternalAuthenticationDTO;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
public class ExternalAuthenticationResponse extends BaseSuccessResponse implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 6250708319265306389L;
    
    @JsonProperty("data")
    private org.example.synergy.dto.response.external.ExternalAuthenticationDTO data;
    
    public ExternalAuthenticationResponse(ExternalAuthenticationDTO data) {
        this.data = data;
    }
}
