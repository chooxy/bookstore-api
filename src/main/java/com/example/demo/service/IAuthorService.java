/* (C)2022 */
package com.example.demo.service;

import com.example.demo.model.Author;
import java.util.List;
import java.util.Map;

public interface IAuthorService {
    List<Author> getAllAuthors();

    Author getAuthorByName(String name);

    Author addAuthor(String name, Map<String, Object> authorRequest);

    Author updateAuthor(String name, Map<String, Object> authorRequest);
}
