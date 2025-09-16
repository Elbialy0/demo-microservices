package com.booky.email_service.listener;

import com.booky.email_service.config.RabbitMQConfigurations;
import com.booky.email_service.dto.RabbitMQMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    @RabbitListener(queues = RabbitMQConfigurations.EMAIL_QUEUE)
    public void listen(RabbitMQMessage message){

    }
}
