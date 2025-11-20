/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.synergy.dto.UserListDTO;
import org.example.synergy.dto.response.BaseSuccessResponse;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class UserListResponse extends BaseSuccessResponse implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 3685098245956686952L;
    
    @Schema(description = "total", example = "100")
    @JsonProperty("total")
    private long total;
    
    @Schema(description = "data")
    @JsonProperty("data")
    private List<UserListDTO> data = new ArrayList<>();
}
