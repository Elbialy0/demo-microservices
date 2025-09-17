package com.booky.email_service.service;

import com.booky.email_service.dto.RabbitMQBookMessage;

import java.util.Map;

public interface EmailPrep {
    Map<String,Object> prepareVerificationEmail(String otp);

    Map<String, Object> prepareBookEmail(RabbitMQBookMessage message);
}
