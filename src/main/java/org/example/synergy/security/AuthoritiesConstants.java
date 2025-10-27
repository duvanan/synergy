/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {
    
    private AuthoritiesConstants() {
    }
    
    public static final String ADMIN = "ROLE_ADMIN";
    
    public static final String USER = "ROLE_USER";
    
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    
    public static final String USERNAME_KEY = "username";
    
    public static final String TIME_ZONE_KEY = "time_zone";
    
    public static final String AUTHORIZATION_HEADER = "Authorization";
    
    public static final String BEARER_TOKEN_PREFIX = "Bearer ";
    
    public static final String TOKEN_TYPE = "Bearer";
}
