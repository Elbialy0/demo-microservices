package com.booky.email_service.listener;

import com.booky.email_service.config.RabbitMQConfigurations;
import com.booky.email_service.dto.RabbitMQMessage;
import com.booky.email_service.service.EmailPrep;
import com.booky.email_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageListener {
    public static final String SUBJECT = "Verification Email";
    public static final String VERIFICATION_TEMPLATE_HTML = "verification-template.html";
    private final EmailService emailService;
    private final EmailPrep emailPrep;
    @RabbitListener(queues = RabbitMQConfigurations.EMAIL_QUEUE)
    public void listen(RabbitMQMessage message){
        log.info("Received message: {}", message);
        emailService.sendEmail(emailPrep.prepareVerificationEmail(message.getMessage()),message.getEmail()
        , SUBJECT, VERIFICATION_TEMPLATE_HTML);
    }
}
