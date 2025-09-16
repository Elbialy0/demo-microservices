package com.booky.user_service.service.impl;

import com.booky.user_service.model.Token;
import com.booky.user_service.model.User;
import com.booky.user_service.repository.TokenRepository;
import com.booky.user_service.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl  implements TokenService {
    private final TokenRepository tokenRepository;
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public Token createToken(User user) {
        String sixDigitCode = String.format("%06d", RANDOM.nextInt(1_000_000));
        Token token = Token.builder()
                .token(sixDigitCode)
                .expiryDate(LocalDateTime.now().plusDays(1))
                .user(user)
                .build();
        return tokenRepository.save(token);
    }
}
