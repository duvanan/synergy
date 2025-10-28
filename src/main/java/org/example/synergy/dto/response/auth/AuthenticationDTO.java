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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 5143605167552195129L;
    
    private Long id;
    
    private String code;
    
    private String username;
    
    private String fullName;
    
    private String email;
    
    private String phoneNumber;
    
    private Boolean isAssign;
    
    private String regionCode;
    
    private Integer userType;
    
    private String accessToken;
    
    private String refreshToken;
    
    private List<String> permissions;
}
