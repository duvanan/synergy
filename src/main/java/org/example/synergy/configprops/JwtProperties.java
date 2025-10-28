/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.configprops;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Properties specific to Rfias.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@Slf4j
@Data
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "application.security.authentication.jwt")
@Component
public class JwtProperties {
    
    private String secretKey;
    
    private long expiration;
    
    private long refreshTokenExpiration;
}
