/* (C)2022 */
package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AuthorBook {
    @Id @GeneratedValue private Long id;
    private Long bookId;
    private Long authorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public AuthorBook() {}

    @Override
    public String toString() {
        return "BookAuthor{" + "id=" + id + ", bookId=" + bookId + ", authorId=" + authorId + '}';
    }

    public AuthorBook(Long authorId, Long bookId) {
        this.bookId = bookId;
        this.authorId = authorId;
    }
}
