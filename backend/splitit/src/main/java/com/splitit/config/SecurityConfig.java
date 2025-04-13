package com.splitit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/register", "/dashboard", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            )

            // ⚠️ Desactivamos el login automático de Spring Security
            .formLogin(form -> form.disable())
            .logout(logout -> logout.disable()); // También desactivamos el logout automático

        return http.build();
    }
}
