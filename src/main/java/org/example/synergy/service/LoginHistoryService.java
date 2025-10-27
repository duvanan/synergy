/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.service;

import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import org.example.synergy.dto.request.loginhistory.LoginHistoryDTO;
import org.example.synergy.dto.request.loginhistory.LoginHistoryListDTO;


public interface LoginHistoryService {
    
    void createLoginHistory(LoginHistoryDTO loginHistoryDTO);
    
    List<LoginHistoryListDTO> getLoginHistories(LoginHistorySearch search);
    
    int countTotalLoginHistories(LoginHistorySearch search);
    
    void exportToExcel(LoginHistorySearch search, HttpServletResponse response);
}
