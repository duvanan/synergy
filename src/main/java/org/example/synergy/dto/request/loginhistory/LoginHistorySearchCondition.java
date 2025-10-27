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
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import com.viettel.vtit.rfias.model.dto.PaginationInfo;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class LoginHistorySearchCondition implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 678144304932442711L;
    
    private String username;
    
    private String ipAddress;
    
    private List<String> status;
    
    private String fromDate;
    
    private String toDate;
    
    private String collate;
    
    private PaginationInfo paginationInfo;
}
