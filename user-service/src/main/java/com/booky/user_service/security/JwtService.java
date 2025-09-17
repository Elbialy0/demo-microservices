package com.booky.user_service.security;

import com.booky.user_service.model.Role;
import com.booky.user_service.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {
    @Value("${secret.key}")
    private  String SECRET;
    private final Long EXPIRATION_TIME = 1000000L;

    public String generateToken(User user){
        List<String > roles = user.getRoles().stream()
                .map(Role::getRoleName)
                .toList();
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("authorities",roles)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(getKey())
                .compact();

    }
    public SecretKey getKey(){
        byte[] key = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(key);
    }
}
