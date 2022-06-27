/* (C)2022 */
package com.example.demo.service;

import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService implements IAuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAllAuthors() {
        return (List<Author>) authorRepository.findAll();
    }

    @Override
    public Author getAuthorByName(String name) {
        Optional<Author> author = authorRepository.findAuthorByName(name);
        if (author.isEmpty()) return null;
        return author.get();
    }

    @Override
    public Author addAuthor(String name, Map<String, Object> authorRequest) {
        // Author name must be unique
        if (authorRepository.existsByName(name)) return null;

        Author author;
        try {
            author = new Author(authorRequest);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(String name, Map<String, Object> authorRequest) {
        // Author must already exist
        Optional<Author> oldAuthor = authorRepository.findAuthorByName(name);
        if (oldAuthor.isEmpty()) return null;

        Author newAuthor;
        try {
            newAuthor = new Author(authorRequest);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

        // Save updated author details to existing author's id
        newAuthor.setId(oldAuthor.get().getId());
        return authorRepository.save(newAuthor);
    }
}
