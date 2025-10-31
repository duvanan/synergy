///*
// * VIETTEL SOFTWARE (VTIT)
// *
// * COPYRIGHT NOTICE:
// * All content including source code, documentation, and other information is the property of RFIAS.
// * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
// * Permission for any use must be obtained in writing from RFIAS.
// */
//package org.example.synergy.config;
//
//import lombok.RequiredArgsConstructor;
//
//import org.example.synergy.security.AccessDeniedHandlerImpl;
//import org.example.synergy.security.AuthenticationEntryPointImpl;
//import org.example.synergy.security.AuthoritiesConstants;
//import org.example.synergy.security.CustomAuthenticationProvider;
//import org.example.synergy.security.jwt.JwtAuthenticationFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//@RequiredArgsConstructor
//public class SecurityConfiguration {
//
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    private final AuthenticationEntryPointImpl authenticationEntryPoint;
//
//    private final AccessDeniedHandlerImpl accessDeniedHandler;
//
//    private final CustomAuthenticationProvider customAuthenticationProvider;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .cors(Customizer.withDefaults())
//            .csrf(AbstractHttpConfigurer::disable)
//            .authorizeHttpRequests(
//                    auth ->
//                    // prettier-ignore
//                    auth
//                        .requestMatchers("/api/auth/**").permitAll()
//                        .requestMatchers("/api/external/auth/login").permitAll()
//                        .requestMatchers("/api/admin/files/**").permitAll()
//                        .requestMatchers("/uploads/**").permitAll()
//                        .requestMatchers("/ws/**").permitAll()
//                        .requestMatchers("/api/admin/**").authenticated()
//                        .requestMatchers("/api/external/**").authenticated()
//                        .requestMatchers("/v3/api-docs/**").permitAll()
//                        .requestMatchers("swagger-ui/**").permitAll()
//                        .requestMatchers("/management/health").permitAll()
//                        .requestMatchers("/management/health/**").permitAll()
//                        .requestMatchers("/management/info").permitAll()
//                        .requestMatchers("/management/prometheus").permitAll()
//                        .requestMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN))
//            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .authenticationProvider(customAuthenticationProvider)
//            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//            .exceptionHandling(exception -> exception
//                .authenticationEntryPoint(authenticationEntryPoint)
//                .accessDeniedHandler(accessDeniedHandler));
//
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//}
