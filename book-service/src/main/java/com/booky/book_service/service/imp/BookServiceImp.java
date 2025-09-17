package com.booky.book_service.service.imp;

import com.booky.book_service.config.RabbitMQConfigurations;
import com.booky.book_service.dto.BookDto;
import com.booky.book_service.dto.RabbitMQBookMessage;
import com.booky.book_service.model.Book;
import com.booky.book_service.repository.BookRepository;
import com.booky.book_service.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImp implements BookService {
    public static final String URL = "http://localhost:8080/api/user/auth/emails";
    public static final String ORDER_S = "http://localhost:3030/api/order/%s";
    private final BookRepository bookRepository;
    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void addBook(BookDto book) {
       Book addedBook = bookRepository.save(Book.builder()
                .name(book.bookName())
                .price(book.price())
                .author(book.authorName())
                .isbn(UUID.randomUUID().toString())
                .build());
        log.info("Book added with name {}", book.bookName());
        // todo send email for all user
        ResponseEntity<List<String>> response = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {}
        );

        List<String> emails = response.getBody();

        if (emails == null) {
            throw new RuntimeException("Error fetching emails");
        }
        RabbitMQBookMessage message = new RabbitMQBookMessage();
        message.setBookName(addedBook.getName());
        message.setBookAuthor(addedBook.getAuthor());
        message.setBookISBN(addedBook.getAuthor());
        message.setBookUrl(String.format(ORDER_S, addedBook.getId()));
        message.setEmails(emails);
        rabbitTemplate.convertAndSend(RabbitMQConfigurations.USER_EXCHANGE,
                RabbitMQConfigurations.ROUTING_KEY, message);

    }

    @Override
    public BookDto getBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        return new BookDto(book.getName(),book.getAuthor(),book.getPrice());
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        bookRepository.delete(book);
        log.info("deleted book with id {}",id);

    }
}