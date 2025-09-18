package com.booky.book_service.controller;

import com.booky.book_service.dto.BookDto;
import com.booky.book_service.model.Book;
import com.booky.book_service.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {
    private final BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<Void> addNewBook(@RequestBody BookDto book){
        log.info("Adding new book {}", book);
        bookService.addBook(book);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(bookService.getBook(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

}
