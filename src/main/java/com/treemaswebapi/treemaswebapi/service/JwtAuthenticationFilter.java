// package com.treemaswebapi.treemaswebapi.service;

// import java.nio.file.attribute.UserPrincipal;
// import java.security.Principal;
// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.http.HttpHeaders;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.filter.OncePerRequestFilter;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.JwtException;
// import io.jsonwebtoken.io.IOException;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// import com.treemaswebapi.treemaswebapi.service.JwtService;

// public class JwtAuthenticationFilter extends OncePerRequestFilter{

//     private final JwtService jwtService;

//     public JwtAuthenticationFilter(JwtService jwtService){
//         this.jwtService = jwtService;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, java.io.IOException, ServletException {
//         JwtService jwtService = new JwtService();
//         String token = jwtService.extractTokenFromRequest(request);
//         if (token != null) {
//             try {
//                 Claims claims = jwtService.validateTokenAndGetClaims(token);
//                 Authentication authentication = createAuthenticationFromClaims(claims);
//                 SecurityContextHolder.getContext().setAuthentication(authentication);
//             } catch (JwtException e) {
//                 SecurityContextHolder.clearContext();
//                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                 return;
//             }
//         }

//         filterChain.doFilter(request, response);
//     }

//     private Authentication createAuthenticationFromClaims(Claims claims) {
//         String username = claims.getSubject(); // Assuming the subject is the username
//         List<GrantedAuthority> authorities = extractAuthoritiesFromToken(claims); // Implement this method to extract authorities

//     Principal userPrincipal = request.getUserPrincipal(username, authorities);
//     return new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
//     }

//     private List<GrantedAuthority>  extractAuthoritiesFromToken(Claims claims) {
//             List<GrantedAuthority> authorities = new ArrayList<>();

//         // Assuming authorities are stored in the "authorities" claim as a JSON array
//         List<String> authoritiesClaim = claims.get("authorities", List.class);

//         if (authoritiesClaim != null) {
//             for (String authority : authoritiesClaim) {
//                 authorities.add(new SimpleGrantedAuthority(authority));
//             }
//         }

//         return authorities;
//     }
// }
