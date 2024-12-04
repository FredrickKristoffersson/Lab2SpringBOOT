package org.example.lab2springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.*;

@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                        .csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers(GET, "/api/categories").permitAll()
                                .requestMatchers(GET, "/api/categories/{name}").permitAll()
                                .requestMatchers(POST, "/api/categories").hasAuthority("SCOPE_admin")
                                .requestMatchers(GET, "/api/locations").permitAll()
                                .requestMatchers(GET, "/api/locations/area/{lon}/{lat}").permitAll()
                                .requestMatchers(POST, "/api/locations").hasAuthority("SCOPE_user")
                                .anyRequest().authenticated()
                        )
                        .oauth2ResourceServer(oauth2 -> oauth2.jwt());
                return http.build();
        }
}

