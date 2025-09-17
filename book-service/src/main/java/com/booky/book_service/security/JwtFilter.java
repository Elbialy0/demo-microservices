package com.booky.book_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Filter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        // todo implement blacklisting of tokens
        if(request.getHeader("Authorization") == null){
            // todo exception handling
            throw new RuntimeException("Unauthorized");
        }
        final String TOKEN = request.getHeader("Authorization");
        if(!TOKEN.startsWith("Bearer ")){
            // todo exception handling
            throw new RuntimeException("Invalid token");
        }
        final String token = TOKEN.substring(7);
        try {
            if(SecurityContextHolder.getContext()==null){
                Authentication authentication = jwtService.validate(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex){
            // todo exception handling
            throw new RuntimeException("Invalid token");
        }

    }
}
