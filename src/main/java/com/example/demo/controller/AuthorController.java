/* (C)2022 */
package com.example.demo.controller;

import com.example.demo.model.Author;
import com.example.demo.service.AuthorService;
import com.example.demo.service.IAuthorService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final IAuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity getAllAuthors() {
        return ResponseEntity.status(HttpStatus.OK).body(authorService.getAllAuthors());
    }

    @GetMapping("/{authorName}")
    public ResponseEntity getAuthorByName(@PathVariable String authorName) {
        Author author = authorService.getAuthorByName(authorName);
        if (author == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No author with name '" + authorName + "' found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(author);
    }

    @PostMapping("/{authorName}")
    public ResponseEntity addAuthor(
            @PathVariable String authorName, @RequestBody Map<String, Object> authorRequest) {
        Author author = authorService.addAuthor(authorName, authorRequest);
        if (author == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Failed to add author");
        }
        return ResponseEntity.status(HttpStatus.OK).body(author);
    }

    @PutMapping("/{authorName}")
    public ResponseEntity updateBook(
            @PathVariable String authorName, @RequestBody Map<String, Object> updateAuthorRequest) {
        Author author = authorService.updateAuthor(authorName, updateAuthorRequest);
        if (author == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to update author");
        }
        return ResponseEntity.status(HttpStatus.OK).body(author);
    }
}
