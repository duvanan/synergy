/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.controller;

import lombok.RequiredArgsConstructor;

import org.example.synergy.dto.request.loginhistory.AuthenticationRequest;
import org.example.synergy.dto.response.auth.AuthenticationDTO;
import org.example.synergy.dto.response.auth.AuthenticationResponse;
import org.example.synergy.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;
    
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request) {
        AuthenticationDTO authentication = authenticationService.login(request);
        
        return ResponseEntity.ok(AuthenticationResponse.builder()
            .id(authentication.getId())
            .code(authentication.getCode())
            .username(authentication.getUsername())
            .email(authentication.getEmail())
            .phoneNumber(authentication.getPhoneNumber())
            .fullName(authentication.getFullName())
            .isAssign(authentication.getIsAssign())
            .regionCode(authentication.getRegionCode())
            .userType(authentication.getUserType())
            .accessToken(authentication.getAccessToken())
            .refreshToken(authentication.getRefreshToken())
            .permissions(authentication.getPermissions())
            .build());
    }

}
