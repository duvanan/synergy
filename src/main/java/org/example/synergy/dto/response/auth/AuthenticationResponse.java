/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.dto.response.auth;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.synergy.dto.response.BaseSuccessResponse;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse extends BaseSuccessResponse implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 6090456833495204882L;
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("user_code")
    private String code;
    
    @JsonProperty("username")
    private String username;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("full_name")
    private String fullName;
    
    @JsonProperty("phone_number")
    private String phoneNumber;
    
    @JsonProperty("is_assign")
    private Boolean isAssign;
    
    @JsonProperty("region_code")
    private String regionCode;
    
    @JsonProperty("user_type")
    private Integer userType;
    
    @JsonProperty("access_token")
    private String accessToken;
    
    @JsonProperty("refresh_token")
    private String refreshToken;
    
    @JsonProperty("permissions")
    private List<String> permissions;
}
