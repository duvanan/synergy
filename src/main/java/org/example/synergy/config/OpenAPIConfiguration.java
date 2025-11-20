/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.example.synergy.contants.OpenAPIConstants;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class OpenAPIConfiguration {
    
    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
            .group(OpenAPIConstants.GroupedOpenApi.TITLE.getValue())
            .packagesToScan(OpenAPIConstants.GroupedOpenApi.PACKAGES_TO_SCAN.getValue())
            .pathsToMatch(OpenAPIConstants.GroupedOpenApi.PATHS_TO_MATCH.getValue())
            .build();
    }
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title(OpenAPIConstants.OpenAPI.TITLE.getValue())
                .description(OpenAPIConstants.OpenAPI.DESCRIPTION.getValue())
                .version(OpenAPIConstants.OpenAPI.VERSION.getValue())
                .contact(new Contact().name(OpenAPIConstants.OpenAPI.CONTACT_NAME.getValue())
                    .email(OpenAPIConstants.OpenAPI.EMAIL.getValue())))
            .components(new Components()
                .addSecuritySchemes(OpenAPIConstants.SecurityScheme.JWT.getValue(), new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme(OpenAPIConstants.SecurityScheme.SCHEME.getValue())
                    .bearerFormat(OpenAPIConstants.SecurityScheme.JWT.getValue())
                    .in(SecurityScheme.In.HEADER)))
            .addSecurityItem(new SecurityRequirement().addList(OpenAPIConstants.SECURITY_REFERENCE,
                    new ArrayList<>(List.of(OpenAPIConstants.AUTHORIZATION_SCOPE))));
    }
}
