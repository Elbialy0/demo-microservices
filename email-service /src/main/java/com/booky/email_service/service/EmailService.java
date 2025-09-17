package com.booky.email_service.service;

import com.booky.email_service.dto.RabbitMQMessage;

import java.util.Map;

public interface EmailService {

    void sendEmail(Map<String, Object> stringObjectMap, String email, String subject, String verificationTemplateHtml);
}
