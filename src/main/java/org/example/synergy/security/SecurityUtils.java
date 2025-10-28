/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.security;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.example.synergy.contants.Constants;
import org.example.synergy.contants.JwtConstants;
import org.example.synergy.util.ObjectMapperUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {
    
    private SecurityUtils() {
    }
    
    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }
    
    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }
    
    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user.
     */
    public static Optional<String> getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .filter(authentication -> authentication.getCredentials() instanceof String)
            .map(authentication -> (String) authentication.getCredentials());
    }
    
    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise.
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && getAuthorities(authentication).noneMatch(AuthoritiesConstants.ANONYMOUS::equals);
    }
    
    /**
     * Checks if the current user has any of the authorities.
     *
     * @param authorities the authorities to check.
     * @return true if the current user has any of the authorities, false otherwise.
     */
    public static boolean hasCurrentUserAnyOfAuthorities(String... authorities) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && getAuthorities(authentication)
            .anyMatch(authority -> Arrays.asList(authorities).contains(authority)));
    }
    
    /**
     * Checks if the current user has none of the authorities.
     *
     * @param authorities the authorities to check.
     * @return true if the current user has none of the authorities, false otherwise.
     */
    public static boolean hasCurrentUserNoneOfAuthorities(String... authorities) {
        return !hasCurrentUserAnyOfAuthorities(authorities);
    }
    
    /**
     * Checks if the current user has a specific authority.
     *
     * @param authority the authority to check.
     * @return true if the current user has the authority, false otherwise.
     */
    public static boolean hasCurrentUserThisAuthority(String authority) {
        return hasCurrentUserAnyOfAuthorities(authority);
    }
    
    private static Stream<String> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority);
    }
    
    public static String decodeTimeZoneFromToken() {
        return decodeToken(JwtConstants.TIME_ZONE_KEY).orElse(Constants.DEFAULT_TIME_ZONE);
    }
    
    public static Optional<String> decodeToken(String key) {
        String token = getCurrentUserJWT().orElse(null);
        if (StringUtils.isBlank(token)) {
            return Optional.empty();
        }
        
        String claimsStr;
        try {
            claimsStr = decodeJWT(token);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Optional.empty();
        }
        
        if (StringUtils.isBlank(claimsStr)) {
            return Optional.empty();
        }
        
        Map<String, Object> claimMap = ObjectMapperUtil.jsonToObject(claimsStr, new TypeReference<>() {
        });
        Object value = claimMap.get(key);
        return value != null ? Optional.of(value.toString()) : Optional.empty();
    }
    
    private static String decodeJWT(String jwtToken) {
        int firstPeriod = jwtToken.indexOf('.');
        int lastPeriod = jwtToken.lastIndexOf('.');
        if (firstPeriod == -1 || lastPeriod == -1 || firstPeriod == lastPeriod) {
            throw new IllegalArgumentException("Invalid JWT token format");
        }
        
        String base64EncodedClaims = jwtToken.substring(firstPeriod + 1, lastPeriod);
        Base64.Decoder decoder = Base64.getUrlDecoder();
        byte[] claimsBytes = decoder.decode(base64EncodedClaims);
        return new String(claimsBytes, StandardCharsets.UTF_8);
    }
    
    public static List<GrantedAuthority> stringsToAuthorities(Set<String> keys) {
        return keys.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }
    
    public static List<String> authoritiesToStrings(List<GrantedAuthority> authorities) {
        return authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    }
    
    public static List<String> getCurrentUserAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return List.of();
        }
        return authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    }
    
    public static Optional<Long> resolveCurrentUserId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof UserDetailsImpl userDetailsImpl) {
            return Optional.of(userDetailsImpl.getId());
        }
        
        return Optional.empty();
    }
    
    public static Optional<String> resolveCurrentUserFullName() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof UserDetailsImpl userDetailsImpl) {
            return Optional.of(userDetailsImpl.getFullName());
        }
        
        return Optional.empty();
    }
}
