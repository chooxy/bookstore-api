/* (C)2022 */
package com.example.demo.repository;

import com.example.demo.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

    Optional<Book> findBookById(Long id);

    Optional<Book> findBookByIsbn(String bookIsbn);

    Boolean existsByIsbn(String bookIsbn);

    List<Book> findBooksByIdIn(List<Long> id);

    List<Book> findBooksByTitle(String title);
}
