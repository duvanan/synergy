/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.security;

import java.io.IOException;
import java.util.Locale;

import lombok.extern.slf4j.Slf4j;

import org.example.synergy.contants.ErrorKeyConstants;
import org.example.synergy.exceptions.ErrorDetailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        ErrorDetailResponse body = ErrorDetailResponse.builder()
            .timestamp(System.currentTimeMillis())
            .path(request.getServletPath())
            .error(HttpStatus.UNAUTHORIZED.name().toLowerCase(Locale.ENGLISH))
            .errorMessage(authException.getMessage())
            .errorKey(ErrorKeyConstants.Auth.TOKEN_INVALID_OR_EXPIRED)
            .build();
        
        final ObjectMapper mapper = new ObjectMapper();
        // register the JavaTimeModule, which enables Jackson to support Java 8 and higher date and time types
        mapper.registerModule(new JavaTimeModule());
        // ask Jackson to serialize dates as strings in the ISO 8601 format
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.writeValue(response.getOutputStream(), body);
    }
}
