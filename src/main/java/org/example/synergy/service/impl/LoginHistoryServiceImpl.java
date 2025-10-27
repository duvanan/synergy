/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.service.impl;

import java.time.Instant;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.synergy.dto.request.loginhistory.LoginHistoryDTO;
import org.example.synergy.service.LoginHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Service class Login History Service Implementation.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LoginHistoryServiceImpl implements LoginHistoryService {
    
    private final LoginHistoryRepository loginHistoryRepository;
    
    private final LoginHistoryExcelService loginHistoryExcelService;
    
    @Override
    @Transactional(transactionManager = "rfiasTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public void createLoginHistory(LoginHistoryDTO loginHistoryDTO) {
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setUsername(loginHistoryDTO.getUsername());
        loginHistory.setUserId(loginHistoryDTO.getUserId());
        loginHistory.setIpAddress(UserInfoUtil.getClientIpAddress());
        loginHistory.setStatus(loginHistoryDTO.getStatus());
        loginHistory.setLocation(UserInfoUtil.getLocationInfo());
        loginHistory.setDevice(UserInfoUtil.getDeviceInfo());
        loginHistory.setBrowser(UserInfoUtil.getBrowserInfo());
        loginHistory.setErrorCode(loginHistoryDTO.getErrorCode());
        loginHistory.setErrorMessage(loginHistoryDTO.getErrorMessage());
        loginHistory.setCreatedAt(Instant.now());
        loginHistoryRepository.save(loginHistory);
    }
    
    @Override
    @Transactional(transactionManager = "rfiasTransactionManager", readOnly = true)
    public List<LoginHistoryListDTO> getLoginHistories(LoginHistorySearch search) {
        LoginHistorySearchCondition condition = LoginHistorySearchCondition.builder()
            .username(search.getUsername())
            .ipAddress(search.getIpAddress())
            .status(search.getStatus())
            .fromDate(search.getFromDate())
            .toDate(search.getToDate())
            .collate(Constants.UTF8MB4_UNICODE_520_CI)
            .paginationInfo(new PaginationInfo(search.getPageNumber(), search.getPageSize()))
            .build();
        
        return loginHistoryRepository.getLoginHistories(condition);
    }
    
    @Override
    @Transactional(transactionManager = "rfiasTransactionManager", readOnly = true)
    public int countTotalLoginHistories(LoginHistorySearch search) {
        LoginHistorySearchCondition condition = LoginHistorySearchCondition.builder()
            .username(search.getUsername())
            .ipAddress(search.getIpAddress())
            .status(search.getStatus())
            .fromDate(search.getFromDate())
            .toDate(search.getToDate())
            .collate(Constants.UTF8MB4_UNICODE_520_CI)
            .build();
        
        return loginHistoryRepository.countTotalLoginHistories(condition);
    }
    
    @Override
    @Transactional(transactionManager = "rfiasTransactionManager", readOnly = true)
    public void exportToExcel(LoginHistorySearch search, HttpServletResponse response) {
        LoginHistorySearchCondition condition = LoginHistorySearchCondition.builder()
            .username(search.getUsername())
            .ipAddress(search.getIpAddress())
            .status(search.getStatus())
            .fromDate(search.getFromDate())
            .toDate(search.getToDate())
            .collate(Constants.UTF8MB4_UNICODE_520_CI)
            .build();
        
        List<LoginHistoryListDTO> loginHistories = loginHistoryRepository.getLoginHistories(condition);
        loginHistoryExcelService.exportToExcel(loginHistories, response);
    }
}
