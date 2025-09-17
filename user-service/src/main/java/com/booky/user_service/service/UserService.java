package com.booky.user_service.service;

import com.booky.user_service.Dto.UserDto;
import com.booky.user_service.Dto.UserLogin;

import java.util.List;

public interface UserService {
    public void register(UserDto userDto);

    void verify(String otp);

    List<String> findAllMails();

    String login(UserLogin user);
}
