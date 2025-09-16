package com.booky.user_service.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigurations {

    public static final String EMAIL_QUEUE = "email-queue";
    public static final String EMAIL_EXCHANGE = "user.exchange";
    public static final String ROUTING_KEY = "routing.key";

    @Bean
    public Queue emailQueue(){
        return new Queue(EMAIL_QUEUE,true);
    }
    @Bean
    public DirectExchange userExchange() {
        return new DirectExchange(EMAIL_EXCHANGE);
    }
    @Bean
    public Binding userBinding(Queue exampleQueue, DirectExchange exampleExchange) {
        return BindingBuilder.bind(exampleQueue)
                .to(exampleExchange)
                .with(ROUTING_KEY);
    }
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        final var template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }


}
