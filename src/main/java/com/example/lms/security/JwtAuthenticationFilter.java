package com.example.lms.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        log.info("Filter hit: {}", request.getServletPath());

        final String authHeader = request.getHeader("Authorization");
        log.info("Auth header: {}", authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            log.info("JWT: {}", jwt);  // 👈

            final String userEmail = jwtService.extractUsername(jwt);
            log.info("Email extracted: {}", userEmail);  // 👈

            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            log.info("User loaded: {}", userDetails.getUsername());  // 👈
            log.info("Authorities: {}", userDetails.getAuthorities());  // 👈

            boolean valid = jwtService.isTokenValid(jwt, userDetails);
            log.info("Token valid: {}", valid);  // 👈

            if (valid) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.info("✅ Auth set for: {}", userEmail);  // 👈
            }

        } catch (BadCredentialsException e) {
            log.error("❌ EXCEPTION TYPE: {}", e.getClass().getName());  // 👈
            log.error("❌ EXCEPTION MSG:  {}", e.getMessage());           // 👈
            log.error("❌ FULL TRACE:", e);                               // 👈
            throw e;

        }

        filterChain.doFilter(request, response);
    }
}