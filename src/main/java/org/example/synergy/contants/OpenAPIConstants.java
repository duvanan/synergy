/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.contants;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class OpenAPIConstants {
    
    @Getter
    public enum GroupedOpenApi {
        TITLE("RFIIS API"),
        PACKAGES_TO_SCAN("com.viettel.vtit.rfias"),
        PATHS_TO_MATCH("/api/**");
        
        private final String value;
        
        GroupedOpenApi(String value) {
            this.value = value;
        }
    }
    
    @Getter
    public enum OpenAPI {
        TITLE("RFIIS API Documentation"),
        DESCRIPTION("Detailed documentation of the API endpoints"),
        VERSION("1.0.0"),
        CONTACT_NAME("Support Team"),
        EMAIL("support@viettel.com.vn");
        
        private final String value;
        
        OpenAPI(String value) {
            this.value = value;
        }
    }
    
    @Getter
    public enum SecurityScheme {
        JWT("JWT"),
        SCHEME("bearer"),
        NAME("Authorization"),
        BEARER_FORMAT("header");
        
        private final String value;
        
        SecurityScheme(String value) {
            this.value = value;
        }
    }
    
    public static final String AUTHORIZATION_SCOPE = "global";
    
    public static final String SECURITY_REFERENCE = "JWT";
}
