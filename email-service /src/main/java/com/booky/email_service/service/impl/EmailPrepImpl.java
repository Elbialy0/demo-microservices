package com.booky.email_service.service.impl;

import com.booky.email_service.dto.RabbitMQBookMessage;
import com.booky.email_service.service.EmailPrep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailPrepImpl implements EmailPrep {

    public static final String URL = "http://localhost:8080/auth/verification/%s";

    @Override
    public Map<String,Object> prepareVerificationEmail(String otp) {
        return Map.of("OTP",otp,"url",String.format(URL,otp));
    }

    @Override
    public Map<String, Object> prepareBookEmail(RabbitMQBookMessage message) {

        Map<String ,Object> model = new HashMap<>();
        model.put("book-name",message.getBookName());
        model.put("book-author",message.getBookAuthor());
        model.put("book-isbn",message.getBookISBN());
        model.put("book-url",message.getBookUrl());
        return model;
    }
}
