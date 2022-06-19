/* (C)2022 */
package com.example.demo.repository;

import com.example.demo.model.Author;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    Optional<Author> findById(Long id);

    Optional<Author> findAuthorByName(String name);

    Boolean existsByName(String name);

    List<Author> findAuthorsByNameIn(List<String> names);

    List<Author> findAuthorsByIdIn(List<Long> ids);
}
