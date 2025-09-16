package com.booky.user_service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RabbitMQMessage {
    private String messageId;
    private String message;
    private Date messageDate;
    private String email;
}
