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
                .anyRequest().permitAll()  // 🔓 Permitir todo sin seguridad
            )
            .formLogin(form -> form.disable())  // ❌ Desactivar login automático de Spring Security
            .logout(logout -> logout.disable()); // ❌ Desactivar logout automático

        return http.build();
    }
}
