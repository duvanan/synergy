package org.example.synergy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {}) // bật CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()   // Cho phép toàn bộ API
                        .anyRequest().permitAll()
                )
                .formLogin(login -> login.disable())  // tắt form login mặc định
                .httpBasic(basic -> basic.disable())  // tắt basic auth
                .logout(logout -> logout.disable());  // tắt logout

        return http.build();
    }
}
