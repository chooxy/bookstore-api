/* (C)2022 */
package com.example.demo;

import com.example.demo.model.Author;
import com.example.demo.model.AuthorBook;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorBookRepository;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AuthorBookRepository authorBookRepository;

    @Autowired
    public DatabaseLoader(
            BookRepository bookRepository,
            AuthorRepository authorRepository,
            AuthorBookRepository authorBookRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.authorBookRepository = authorBookRepository;
    }

    @Override
    public void run(String... args) {
        Author savedAuthor = this.authorRepository.save(new Author("Bob", "01/01/1990"));

        Book savedBook =
                this.bookRepository.save(
                        new Book("1234567890123", "First Book", 2020, 19.99, "History"));

        AuthorBook savedAuthorBook =
                this.authorBookRepository.save(
                        new AuthorBook(savedAuthor.getId(), savedBook.getId()));

        System.out.println("Saved author: " + savedAuthor);
        System.out.println("Saved book: " + savedBook);
        System.out.println("Saved authorBook: " + savedAuthorBook);
    }
}
