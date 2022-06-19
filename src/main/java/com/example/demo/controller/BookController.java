/* (C)2022 */
package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import com.example.demo.service.IBookService;
import com.example.demo.util.JWT;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final IBookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
    }

    @GetMapping("/title/{title}")
    public ResponseEntity getBooksByTitle(@PathVariable String title) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getBooksByTitle(title));
    }

    @GetMapping("/author/{authorName}")
    public ResponseEntity getBooksByAuthor(@PathVariable String authorName) {
        List<Book> books = bookService.getBooksByAuthorName(authorName);
        if (books == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No author with name '" + authorName + "' found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity getBooksByIsbn(@PathVariable String isbn) {
        Book book = bookService.getBookByIsbn(isbn);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No book with ISBN '" + isbn + "' found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @PostMapping("/{isbn}")
    public ResponseEntity addBook(
            @PathVariable String isbn, @RequestBody Map<String, Object> addBookRequest) {
        Book book = bookService.addBook(isbn, addBookRequest);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Failed to add book");
        }
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity updateBook(
            @PathVariable String isbn, @RequestBody Map<String, Object> updateBookRequest) {
        Book book = bookService.updateBook(isbn, updateBookRequest);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to update book");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Updated book");
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity deleteBook(
            @RequestHeader(name = "Authorization") String token, @PathVariable String isbn) {
        List<String> groups = JWT.getGroupsFromJwt(token.split(" ")[1]);
        if (groups.contains("ADMIN")) {
            this.bookService.deleteBook(isbn);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorised to delete book");
    }
}
