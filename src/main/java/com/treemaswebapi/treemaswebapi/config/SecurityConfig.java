package com.treemaswebapi.treemaswebapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.treemaswebapi.treemaswebapi.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;


@Configuration
public class SecurityConfig {
    @Autowired
    private JwtService jwtService;
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    //     http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.requestMatchers("/api/login", "/api/register").permitAll()
    //                                                                              .requestMatchers("/absen/project-list").authenticated()
    //     )
    //     .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
    //     .csrf(csrf -> csrf.disable());
    
    // return http.build();
    
    
    // }

    // public String extractTokenFromHeader(HttpServletRequest request) {
    //     String header = request.getHeader("Authorization");
    //     if (header != null && header.startsWith("Bearer ")) {
    //         return header.substring(7); // Extract the token part after "Bearer "
    //     } else {
    //         return null; // No token found in the header
    //     } // Extract the token from the header.
    // }
    

    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   

}

