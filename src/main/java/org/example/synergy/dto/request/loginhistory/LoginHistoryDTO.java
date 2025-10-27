/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.dto.request.loginhistory;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.synergy.contants.enums.LoginStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginHistoryDTO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 6421224175916755097L;
    
    private String username;
    
    private Long userId;
    
    private LoginStatus status;
    
    private String errorCode;
    
    private String errorMessage;
}
