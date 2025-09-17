package com.booky.email_service.listener;

import com.booky.email_service.config.RabbitMQConfigurations;
import com.booky.email_service.dto.RabbitMQBookMessage;
import com.booky.email_service.dto.RabbitMQMessage;
import com.booky.email_service.service.EmailPrep;
import com.booky.email_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;


@Component
@Slf4j
@RequiredArgsConstructor
public class MessageListener {
    public static final String BOOK_QUEUE = "book-queue";
    public static final String SUBJECT = "Verification Email";
    public static final String VERIFICATION_TEMPLATE_HTML = "verification-template.html";
    public static final String BOOK_HTML = "book.html";
    public static final String A_NEW_BOOK_HAS_BEEN_ADDED_TO_THE_LIBRARY = "A new book has been added to the library";
    private final EmailService emailService;
    private final EmailPrep emailPrep;
    @RabbitListener(queues = RabbitMQConfigurations.EMAIL_QUEUE)
    public void listen(RabbitMQMessage message){
        log.info("Received message: {}", message);
        emailService.sendEmail(emailPrep.prepareVerificationEmail(message.getMessage()),message.getEmail()
        , SUBJECT, VERIFICATION_TEMPLATE_HTML);
    }
    @RabbitListener(queues = BOOK_QUEUE)
    public void listenToBookQueue(RabbitMQBookMessage message){
        log.info("Received message: {}", message);
        Map<String , Object> model = emailPrep.prepareBookEmail(message);
        message.getEmails()
                .forEach(email ->
                        emailService.sendEmail(model,email, A_NEW_BOOK_HAS_BEEN_ADDED_TO_THE_LIBRARY,
                                BOOK_HTML));

    }
}
