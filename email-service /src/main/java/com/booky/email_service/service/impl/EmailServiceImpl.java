package com.booky.email_service.service.impl;

import com.booky.email_service.dto.RabbitMQMessage;
import com.booky.email_service.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    public static final String MAIL = "movietech@movieres.com";
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;


    @Override
    public void sendEmail(Map<String, Object> model,
                          String email,
                          String subject,
                          String templateName) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try{
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom(MAIL);
            mimeMessageHelper.setTo(email);
            Context context = new Context();
            context.setVariables(model);
            String text = templateEngine.process(templateName,context);
            mimeMessageHelper.setText(text,true);
            javaMailSender.send(mimeMessage);
            log.info("Email sent successfully");

        }catch (MailException | MessagingException e){
            throw new RuntimeException("Email sending failed. Please try again later. " + e.getMessage());
        }
    }
}
