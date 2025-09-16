package com.booky.user_service.service.impl;

import com.booky.user_service.Dto.UserDto;
import com.booky.user_service.model.Token;
import com.booky.user_service.model.User;
import com.booky.user_service.repository.RoleRepository;
import com.booky.user_service.repository.TokenRepository;
import com.booky.user_service.repository.UserRepository;
import com.booky.user_service.service.TokenService;
import com.booky.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;

    @Override
    public void register(UserDto userDto) {
        if (userRepository.findByUsername(userDto.userName()).isPresent()){
            // todo handle user exist exception
            throw new RuntimeException("Username is exist");
        }
        User user = User.builder().roles(Collections.singletonList(roleRepository.findById(1L).get()))
                .email(userDto.email())
                .password(passwordEncoder.encode(userDto.password()))
                .username(userDto.userName()).build();
        user = userRepository.save(user);
        Token token = tokenService.createToken(user);
        // todo send email with the token

        userRepository.save(user);
    }

    @Override
    public void verify(String otp) {
        Token token = tokenRepository.findByToken(otp)
                .orElseThrow(() -> new RuntimeException("Invalid verification token"));

        if (token.getExpiryDate() != null && token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Verification token has expired");
        }

        User user = token.getUser();
        if (user == null) {
            throw new RuntimeException("Token is not associated with any user");
        }

        user.setEnable(true);
        userRepository.save(user);
        
        tokenRepository.delete(token);
    }
}
