package com.booky.user_service.service;

import com.booky.user_service.model.Token;
import com.booky.user_service.model.User;

public interface TokenService {
    Token createToken(User user);
}
