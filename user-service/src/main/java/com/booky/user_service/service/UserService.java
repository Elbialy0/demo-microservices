package com.booky.user_service.service;

import com.booky.user_service.Dto.UserDto;

public interface UserService {
    public void register(UserDto userDto);

    void verify(String otp);
}
