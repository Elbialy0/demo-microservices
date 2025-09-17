package com.booky.user_service.service.impl;

import com.booky.user_service.Dto.RabbitMQMessage;
import com.booky.user_service.Dto.UserDto;
import com.booky.user_service.config.RabbitMQConfigurations;
import com.booky.user_service.model.Token;
import com.booky.user_service.model.User;
import com.booky.user_service.repository.RoleRepository;
import com.booky.user_service.repository.TokenRepository;
import com.booky.user_service.repository.UserRepository;
import com.booky.user_service.service.TokenService;
import com.booky.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Collections;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void register(UserDto userDto) {
        log.info("Registering user {}", userDto.userName());
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
        log.info("Token created for user {}", userDto.userName());
        // todo send email with the token
        RabbitMQMessage rabbitMQMessage = new RabbitMQMessage();
        rabbitMQMessage.setMessageId(UUID.randomUUID().toString());
        rabbitMQMessage.setMessage(token.getToken());
        rabbitMQMessage.setEmail(user.getEmail());
        rabbitMQMessage.setMessageDate(new Date());
        rabbitTemplate.convertAndSend(RabbitMQConfigurations.EMAIL_EXCHANGE,
                RabbitMQConfigurations.ROUTING_KEY
                ,rabbitMQMessage);

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
