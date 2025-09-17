package com.booky.book_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMQBookMessage {
    private String bookName;
    private String bookAuthor;
    private String bookISBN;
    private String bookUrl;
    private List<String > emails;
}
