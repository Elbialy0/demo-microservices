package com.booky.user_service.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigurations {
    @Bean
    public Queue userQueue(){
        return new Queue("user.queue",true);
    }
    @Bean
    public DirectExchange exampleExchange() {
        return new DirectExchange("user.exchange");
    }
    @Bean
    public Binding exampleBinding(Queue exampleQueue, DirectExchange exampleExchange) {
        return BindingBuilder.bind(exampleQueue)
                .to(exampleExchange)
                .with("routing.key");
    }


}
