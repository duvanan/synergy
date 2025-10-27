/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.dto.response.external;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterferenceDataDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1503904142443956253L;
    
    @JsonProperty("request_id")
    private String requestId;
    
    @JsonProperty("cell_code")
    private String cellCode;
    
    @JsonProperty("rfias_id")
    private String rfiasId;
    
    @JsonProperty("receiving_time")
    private Instant receivingTime;
}
