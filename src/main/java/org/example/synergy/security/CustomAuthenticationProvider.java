///*
// * VIETTEL SOFTWARE (VTIT)
// *
// * COPYRIGHT NOTICE:
// * All content including source code, documentation, and other information is the property of RFIAS.
// * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
// * Permission for any use must be obtained in writing from RFIAS.
// */
//package org.example.synergy.security;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import org.example.synergy.contants.enums.LoginStatus;
//import org.example.synergy.dto.request.loginhistory.LoginHistoryDTO;
//import org.example.synergy.exceptions.ExceptionTranslator;
//import org.example.synergy.exceptions.UnauthorizedException;
//import org.example.synergy.repository.UserRepository;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    private final UserDetailsServiceImpl userDetailsService;
//
//    private final LoginHistoryService loginHistoryService;
//
//    private final UserRepository userRepository;
//
//    private final PasswordEncoder passwordEncoder;
//
//    private final ExceptionTranslator exp;
//
//    @Override
//    @Transactional(transactionManager = "rfiasTransactionManager")
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        try {
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//            if (passwordEncoder.matches(password, userDetails.getPassword())) {
//                userRepository.resetFailedPasswordAttempts(username);
//                return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
//            } else {
//                userRepository.incrementFailedAttemptsAndDeactivateIfNeeded(username);
//
//                // Login history for wrong password
//                LoginHistoryDTO loginHistory = LoginHistoryDTO.builder()
//                    .username(username)
//                    .userId(userRepository.getIdByUsername(username).orElse(null))
//                    .status(LoginStatus.Wrong_password)
//                    .errorCode(LoginCodeEnum.ERR01.getCode())
//                    .errorMessage(LoginCodeEnum.ERR01.getMessage())
//                    .build();
//
//                loginHistoryService.createLoginHistory(loginHistory);
//
//                throw exp.translateWithErrorKey(UnauthorizedException::new, "auth.bad_credentials",
//                        ErrorKeyConstants.Auth.BAD_CREDENTIALS);
//            }
//        } catch (UsernameNotFoundException e) {
//            // Login history for user isn't found
//            LoginHistoryDTO loginHistory = LoginHistoryDTO.builder()
//                .userId(userRepository.getIdByUsername(username).orElse(null))
//                .username(username)
//                .status(LoginStatus.Invalid_username)
//                .errorCode(LoginCodeEnum.ERR02.getCode())
//                .errorMessage(LoginCodeEnum.ERR02.getMessage())
//                .build();
//
//            loginHistoryService.createLoginHistory(loginHistory);
//
//            throw exp.translateWithErrorKey(UnauthorizedException::new, "auth.bad_credentials",
//                    ErrorKeyConstants.Auth.BAD_CREDENTIALS);
//        } catch (UserNotActivatedException e) {
//            // Login history for user isn't activated
//            LoginHistoryDTO loginHistory = LoginHistoryDTO.builder()
//                .username(username)
//                .userId(userRepository.getIdByUsername(username).orElse(null))
//                .status(LoginStatus.Account_locked)
//                .errorCode(LoginCodeEnum.ERR03.getCode())
//                .errorMessage(LoginCodeEnum.ERR03.getMessage())
//                .build();
//
//            loginHistoryService.createLoginHistory(loginHistory);
//
//            throw exp.translateWithErrorKey(UnauthorizedException::new, "auth.user_locked",
//                    ErrorKeyConstants.Auth.USER_LOCKED);
//        } catch (UserAccountLockedException e) {
//            // Login history for user account is locked
//            LoginHistoryDTO loginHistory = LoginHistoryDTO.builder()
//                .username(username)
//                .userId(userRepository.getIdByUsername(username).orElse(null))
//                .status(LoginStatus.Account_temp_locked)
//                .errorCode(LoginCodeEnum.ERR04.getCode())
//                .errorMessage(LoginCodeEnum.ERR04.getMessage())
//                .build();
//
//            loginHistoryService.createLoginHistory(loginHistory);
//
//            throw exp.translateWithErrorKey(UnauthorizedException::new, "auth.user_locked",
//                    ErrorKeyConstants.Auth.USER_LOCKED);
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
//}
