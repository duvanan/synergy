/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.security.jwt;

import java.io.IOException;

import lombok.RequiredArgsConstructor;

import org.example.synergy.contants.ErrorKeyConstants;
import org.example.synergy.exceptions.ExceptionTranslator;
import org.example.synergy.exceptions.UnauthorizedException;
import org.example.synergy.security.AuthoritiesConstants;
import org.example.synergy.security.UserDetailsImpl;
import org.example.synergy.security.UserDetailsServiceImpl;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.apache.commons.lang3.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Our jwt class extends OnePerRequestFilter to be executed on every http request
 * We can also implement the Filter interface (jakarta EE), but Spring gives us a OncePerRequestFilter
 * class that extends the GenericFilterBean, which also implements the Filter interface.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtTokenProvider jwtTokenProvider;
    
    private final UserDetailsServiceImpl userDetailsService;
    
    private final ExceptionTranslator exp;
    
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = resolveToken(request);
        
        if (StringUtils.isNotBlank(jwt) && this.jwtTokenProvider.validateToken(jwt)) {
            String username = jwtTokenProvider.extractUsername(jwt);
            String jti = jwtTokenProvider.extractJti(jwt);
            
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            if (userDetails instanceof UserDetailsImpl userDetail) {
                if (StringUtils.isBlank(jti) || StringUtils.isBlank(userDetail.getJti())
                        || !userDetail.getJti().equals(jti)) {
                    throw exp.translateWithErrorKey(UnauthorizedException::new, "auth.bad_credentials",
                            ErrorKeyConstants.Auth.BAD_CREDENTIALS);
                }
            }
            
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, jwt, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AuthoritiesConstants.AUTHORIZATION_HEADER);
        if (StringUtils.isNotBlank(bearerToken)
                && bearerToken.startsWith(AuthoritiesConstants.BEARER_TOKEN_PREFIX)) {
            return bearerToken.substring(AuthoritiesConstants.BEARER_TOKEN_PREFIX.length());
        }
        
        return null;
    }
}
