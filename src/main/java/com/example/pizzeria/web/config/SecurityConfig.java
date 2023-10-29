package com.example.pizzeria.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(customizeRequests -> {
            customizeRequests
                    .requestMatchers(HttpMethod.GET,"/api/pizzas/**").hasAnyRole("ADMIN", "CUSTOMER")
                    .requestMatchers(HttpMethod.POST,"/api/pizzas/**").hasRole("ADMIN")
                    .requestMatchers("/api/orders/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT).denyAll()
                    .anyRequest().authenticated();
        })
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .cors(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
