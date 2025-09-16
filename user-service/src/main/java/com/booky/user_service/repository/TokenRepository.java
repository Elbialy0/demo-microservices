package com.booky.user_service.repository;

import com.booky.user_service.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TokenRepository  extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String otp);
}
