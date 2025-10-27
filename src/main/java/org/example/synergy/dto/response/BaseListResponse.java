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
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;


@NoArgsConstructor
@AllArgsConstructor
public class BaseListResponse<T> extends BaseSuccessResponse implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 5644450546795979510L;
    
    @JsonProperty("total")
    private long total;
    
    @JsonProperty("data")
    private List<T> data = new ArrayList<>();
}
