/* (C)2022 */
package com.example.demo.service;

import com.example.demo.model.Author;
import com.example.demo.model.AuthorBook;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorBookRepository;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService implements IBookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AuthorBookRepository authorBookRepository;

    @Autowired
    public BookService(
            BookRepository bookRepository,
            AuthorRepository authorRepository,
            AuthorBookRepository authorBookRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.authorBookRepository = authorBookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = (List) bookRepository.findAll();

        for (Book book : books) {
            addAuthorNamesToBook(book);
        }

        return books;
    }

    @Override
    public List<Book> getBooksByAuthorName(String authorName) {
        Optional<Author> author = authorRepository.findAuthorByName(authorName);
        if (author.isEmpty()) {
            return null;
        }
        List<AuthorBook> authorBooks =
                authorBookRepository.findAuthorBooksByAuthorId(author.get().getId());
        List<Book> books =
                bookRepository.findBooksByIdIn(
                        authorBooks.stream().map((AuthorBook::getBookId)).toList());
        for (Book book : books) {
            addAuthorNamesToBook(book);
        }

        return books;
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        List<Book> books = bookRepository.findBooksByTitle(title);
        for (Book book : books) {
            addAuthorNamesToBook(book);
        }

        return books;
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        Optional<Book> book = bookRepository.findBookByIsbn(isbn);

        return book.map(this::addAuthorNamesToBook).orElse(null);
    }

    @Override
    public Book addBook(String isbn, Map<String, Object> bookRequest) {
        // ISBN must be unique
        if (bookRepository.existsByIsbn(isbn)) return null;

        List<String> authorNames = (ArrayList<String>) bookRequest.get("authors");
        // Must have at least one author
        if (authorNames.isEmpty()) return null;

        // Save book
        // TODO: handle malformed input
        Book book = new Book(bookRequest);
        book = bookRepository.save(book);

        // Save new authors
        List<Author> authors = authorRepository.findAuthorsByNameIn(authorNames);
        List<String> newAuthorNames =
                authorNames.stream()
                        .filter(
                                authorName ->
                                        !authors.stream()
                                                .map(Author::getName)
                                                .toList()
                                                .contains(authorName))
                        .toList();
        List<Author> newAuthors = newAuthorNames.stream().map(Author::new).toList();
        for (Author author : authorRepository.saveAll(newAuthors)) {
            authors.add(author);
        }

        // Save author-book pairs
        Long bookId = book.getId();
        List<AuthorBook> authorBooks =
                authors.stream().map(author -> new AuthorBook(author.getId(), bookId)).toList();
        authorBookRepository.saveAll(authorBooks);

        return addAuthorNamesToBook(book);
    }

    // TODO: implement method
    @Override
    public Book updateBook(String isbn, Map<String, Object> bookRequest) {
        return null;
    }

    @Override
    public void deleteBook(String isbn) {
        Optional<Book> book = bookRepository.findBookByIsbn(isbn);
        if (book.isPresent()) {
            bookRepository.delete(book.get());
            authorBookRepository.deleteAuthorBooksByBookId(book.get().getId());
        }
    }

    private Book addAuthorNamesToBook(Book book) {
        List<AuthorBook> authorBooks = authorBookRepository.findAuthorBooksByBookId(book.getId());
        List<Author> authors =
                authorRepository.findAuthorsByIdIn(
                        authorBooks.stream().map(AuthorBook::getAuthorId).toList());
        List<String> authorNames = new ArrayList<>();
        for (Author author : authors) {
            authorNames.add(author.getName());
        }
        book.setAuthorNames(authorNames);

        return book;
    }
}
