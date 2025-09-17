package com.booky.book_service.service.imp;

import com.booky.book_service.dto.BookDto;
import com.booky.book_service.model.Book;
import com.booky.book_service.repository.BookRepository;
import com.booky.book_service.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImp implements BookService {
    private final BookRepository bookRepository;

    @Override
    public void addBook(BookDto book) {
        bookRepository.save(Book.builder()
                .name(book.bookName())
                .price(book.price())
                .author(book.authorName())
                .isbn(UUID.randomUUID().toString())
                .build());
        log.info("Book added with name {}", book.bookName());
        // todo send email for all user

    }
}
