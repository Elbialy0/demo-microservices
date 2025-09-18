package com.booky.book_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.List;

@Service
@Slf4j
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
            List<String > roles = claims.get("authorities", List.class);
            var authorities = roles.stream().map(SimpleGrantedAuthority::new).toList();
            return new UsernamePasswordAuthenticationToken(username,null,authorities);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private SecretKey getSecretKey(){
        byte [] encodedKey = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(encodedKey);
    }
}
