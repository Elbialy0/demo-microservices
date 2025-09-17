package com.booky.user_service.controller;

import com.booky.user_service.Dto.LoginDto;
import com.booky.user_service.Dto.UserDto;
import com.booky.user_service.Dto.UserLogin;
import com.booky.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserDto user) {
        userService.register(user);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/verification/{otp}")
    public ResponseEntity<Void> verification(@PathVariable String otp){
        userService.verify(otp);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/emails")
    public ResponseEntity<List<String>> getEmails(){
        return ResponseEntity.ok().body(userService.findAllMails());
    }
    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody UserLogin user){
        return ResponseEntity.ok().body(new LoginDto(userService.login(user)));

    }
}
