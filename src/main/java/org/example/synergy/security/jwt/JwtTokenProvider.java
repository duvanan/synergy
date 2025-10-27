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
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.synergy.configprops.JwtProperties;
import org.example.synergy.contants.JwtConstants;
import org.example.synergy.security.AuthoritiesConstants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;


@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {
    
    private final JwtProperties jwtProperties;
    
    /**
     * Generate token with given username
     */
    public String generateToken(String jit, String username, String localTimeZone, Boolean loginInternal) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(AuthoritiesConstants.USERNAME_KEY, username);
        claims.put(AuthoritiesConstants.TIME_ZONE_KEY, localTimeZone);
        
        return createToken(claims, jit, username, loginInternal);
    }
    
    /**
     * Create a JWT token with specified claims and subject (username)
     */
    private String createToken(Map<String, Object> claims, String jit, String userName, Boolean loginInternal) {
        JwtBuilder jwtBuilder = Jwts.builder()
            .setClaims(claims)
            .setId(jit)
            .setSubject(userName)
            .setIssuedAt(new Date())
            .signWith(getSigningKey(), SignatureAlgorithm.HS256);
        
        if (!loginInternal) {
            jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()));
        }
        
        return jwtBuilder.compact();
    }
    
    /**
     * Extract the username from the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    /**
     * Extract the expiration date from the token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    /**
     * Extract the JWT ID (jti) from the token
     */
    public String extractJti(String token) {
        return extractClaim(token, Claims::getId);
    }
    
    /**
     * Extract a claim from the token
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    /**
     * Extract all claims from the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
    
    /**
     * Check if the token is expired
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
        
        String authority = claims.get(JwtConstants.AUTHORITIES_KEY) != null
                ? claims.get(JwtConstants.AUTHORITIES_KEY).toString()
                : "";
        
        Collection<? extends GrantedAuthority> authorities = Arrays
            .stream(authority.split(","))
            .filter(auth -> !auth.trim().isEmpty())
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        
        User principal = new User(claims.get(AuthoritiesConstants.USERNAME_KEY).toString(), "", authorities);
        
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
    
    /**
     * Validate the token against user details and expiration
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException ex) {
            log.info("Token has expired: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.info("Unsupported JWT: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.info("Invalid JWT format: {}", ex.getMessage());
        } catch (SignatureException ex) {
            log.info("Invalid JWT signature: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.info("JWT claims string is empty: {}", ex.getMessage());
        } catch (Exception ex) {
            log.info("Invalid token: {}", ex.getMessage());
        }
        return false;
    }
    
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    public Instant calculateTokenExpiration() {
        long expirationDurationMillis = jwtProperties.getExpiration();
        return Instant.now().plusMillis(expirationDurationMillis);
    }
}
