package com.booky.email_service.service.impl;

import com.booky.email_service.service.EmailPrep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailPrepImpl implements EmailPrep {

    public static final String URL = "http://localhost:8080/auth/verification/%s";

    @Override
    public Map<String,Object> prepareVerificationEmail(String otp) {
        return Map.of("OTP",otp,"url",String.format(URL,otp));
    }
}
