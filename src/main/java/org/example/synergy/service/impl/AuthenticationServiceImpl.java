/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.synergy.contants.Constants;
import org.example.synergy.contants.enums.UserType;
import org.example.synergy.dto.request.loginhistory.AuthenticationRequest;
import org.example.synergy.dto.response.auth.AuthenticationDTO;
import org.example.synergy.exceptions.ExceptionTranslator;
import org.example.synergy.repository.UserRepository;
import org.example.synergy.security.AuthoritiesConstants;
import org.example.synergy.security.UserDetailsImpl;
import org.example.synergy.security.jwt.JwtTokenProvider;
import org.example.synergy.security.jwt.RefreshTokenProvider;
import org.example.synergy.service.AuthenticationService;
import org.example.synergy.util.UuidGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;


/**
 * Service class Auth Service Implementation.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    
    private static final String DEFAULT_REGION_CODE = "0";
    
    private final AuthenticationManager authenticationManager;
    
    private final JwtTokenProvider jwtTokenProvider;
    
    private final RefreshTokenProvider refreshTokenProvider;
    

    private final UserRepository userRepository;
    
    private final ExceptionTranslator exp;
    
    private final PasswordEncoder passwordEncoder;
    

    @Transactional
    public AuthenticationDTO login(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));
        
        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        if (authentication.isAuthenticated()) {
            if (authentication.getPrincipal() instanceof UserDetailsImpl userDetailsImpl) {
                authenticationDTO.setId(userDetailsImpl.getId());
                authenticationDTO.setCode(userDetailsImpl.getCode());
                authenticationDTO.setUsername(userDetailsImpl.getUsername());
                authenticationDTO.setFullName(userDetailsImpl.getFullName());
                authenticationDTO.setEmail(userDetailsImpl.getEmail());
                authenticationDTO.setPhoneNumber(userDetailsImpl.getPhoneNumber());
                authenticationDTO.setIsAssign(userDetailsImpl.getIsAssign());
                
                String regionCode = userRepository.getRegionCodeById((userDetailsImpl.getId()));
                authenticationDTO
                    .setRegionCode(!DEFAULT_REGION_CODE.equals(regionCode) ? regionCode : StringUtils.EMPTY);
                authenticationDTO.setUserType(userDetailsImpl.getUserType());
                
                String timeZone = Constants.DEFAULT_TIME_ZONE;
                String jti = StringUtils.isBlank(userDetailsImpl.getJti()) ? UuidGenerator.generateModelId().toString()
                        : userDetailsImpl.getJti();
                String accessToken = jwtTokenProvider.generateToken(jti, userDetailsImpl.getUsername(), timeZone, true);
                String refreshToken = refreshTokenProvider.generateToken(userDetailsImpl.getUsername(), timeZone);
                authenticationDTO.setAccessToken(accessToken);
                authenticationDTO.setRefreshToken(refreshToken);
            }
        }
        
        if (UserType.ADMIN.getValue() == authenticationDTO.getUserType()
                && StringUtils.isNotBlank(authenticationDTO.getUsername())) {
            authenticationDTO.setIsAssign(true);
            authenticationDTO.setRegionCode("");
            authenticationDTO.setPermissions(List.of(AuthoritiesConstants.ADMIN));
            return authenticationDTO;
        }
        return authenticationDTO;
    }

}
