package com.example.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF protection for simplicity (adjust this in production if needed)
            .csrf(csrf -> csrf.disable())
            
            // Define the URL paths and their access rules
            .authorizeHttpRequests(authz -> authz
                // Allow unrestricted access to Swagger UI and API docs
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                
                // Require authentication for all other requests
                .anyRequest().authenticated()
            )
            
            // Use Basic Authentication
            .httpBasic();

        // Build the security filter chain
        return http.build();
    }

    // Bean for password encoding using BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}