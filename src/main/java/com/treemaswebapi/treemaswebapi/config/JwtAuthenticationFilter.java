package com.treemaswebapi.treemaswebapi.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
    public class JwtAuthenticationFilter extends OncePerRequestFilter{
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException, java.io.IOException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
        filterChain.doFilter(request, response); // If there's no token in the header, continue to the next filter.
        return;
        }

    String token = header.replace("Bearer ", ""); // Extract the token from the header.
    
    // Continue with token validation and authentication...
    }
}