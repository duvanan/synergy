/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.dto.request.loginhistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginHistoryListDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 2808555625524221233L;
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("username")
    private String username;
    
    @JsonProperty("ip_address")
    private String ipAddress;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("location")
    private String location;
    
    @JsonProperty("device")
    private String device;
    
    @JsonProperty("browser")
    private String browser;
    
    @JsonProperty("error_code")
    private String errorCode;
    
    @JsonProperty("error_message")
    private String errorMessage;
    
    @JsonProperty("login_time")
    private LocalDateTime loginTime;
}
