/* (C)2022 */
package com.example.demo.service;

import com.example.demo.model.Book;
import java.util.List;
import java.util.Map;

public interface IBookService {
    List<Book> getAllBooks();

    List<Book> getBooksByAuthorName(String authorName);

    List<Book> getBooksByTitle(String title);

    Book getBookByIsbn(String isbn);

    Book addBook(String isbn, Map<String, Object> bookRequest);

    Book updateBook(String isbn, Map<String, Object> bookRequest);

    void deleteBook(String isbn);
}
