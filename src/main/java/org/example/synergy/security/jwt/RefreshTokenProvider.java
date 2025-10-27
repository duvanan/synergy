/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.synergy.configprops.JwtProperties;
import org.example.synergy.security.AuthoritiesConstants;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenProvider {
    
    private final JwtProperties jwtProperties;
    
    public String generateToken(String username, String localTimeZone) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(AuthoritiesConstants.USERNAME_KEY, username);
        claims.put(AuthoritiesConstants.TIME_ZONE_KEY, localTimeZone);
        
        return createToken(claims, username);
    }
    
    /**
     * Create a JWT token with specified claims and subject (user name)
     */
    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
            .setSubject(userName)
            .setClaims(claims)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenExpiration()))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }
    
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
