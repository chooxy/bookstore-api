/* (C)2022 */
package com.example.demo.repository;

import com.example.demo.model.AuthorBook;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorBookRepository extends CrudRepository<AuthorBook, Long> {
    List<AuthorBook> findAuthorBooksByAuthorId(Long authorId);

    List<AuthorBook> findAuthorBooksByBookId(Long bookId);

    @Transactional
    void deleteAuthorBooksByBookId(Long bookId);
}
