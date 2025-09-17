package com.booky.book_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.List;

@Service
public class JwtService {
    @Value("${secret.key}")
    private String SECRET;
    public Authentication validate(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            String username = claims.getSubject();
            List<String > roles = claims.get("roles", List.class);
            var authorities = roles.stream().map(SimpleGrantedAuthority::new).toList();
            return new UsernamePasswordAuthenticationToken(username,null,authorities);

        } catch (Exception e) {
            return null;
        }
    }

    private SecretKey getSecretKey(){
        byte [] encodedKey = SECRET.getBytes();
        return Keys.hmacShaKeyFor(encodedKey);
    }
}
