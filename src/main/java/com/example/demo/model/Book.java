/* (C)2022 */
package com.example.demo.model;

import java.util.List;
import java.util.Map;
import javax.persistence.*;

@Entity
public class Book {
    @Id @GeneratedValue private Long id;
    private String isbn;
    private String title;
    private Integer publishYear;
    private Double price;
    private String genre;
    @Transient private List<String> authorNames;

    public List<String> getAuthorNames() {
        return authorNames;
    }

    public void setAuthorNames(List<String> authorNames) {
        this.authorNames = authorNames;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Book() {}

    public Book(Map<String, Object> bookRequest) {
        if (isValidBookRequest(bookRequest)) {
            this.isbn = bookRequest.get("isbn").toString();
            this.title = bookRequest.get("title").toString();
            this.publishYear = Integer.parseInt(bookRequest.get("publishYear").toString());
            this.price = Double.parseDouble(bookRequest.get("price").toString());
            this.genre = bookRequest.get("genre").toString();
        } else {
            throw new IllegalArgumentException("Missing one or more fields");
        }
    }

    public Book(String isbn, String title, Integer publishYear, Double price, String genre) {
        this.isbn = isbn;
        this.title = title;
        this.publishYear = publishYear;
        this.price = price;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{"
                + "id="
                + id
                + ", isbn='"
                + isbn
                + '\''
                + ", title='"
                + title
                + '\''
                + ", publishYear="
                + publishYear
                + ", price="
                + price
                + ", genre='"
                + genre
                + '\''
                + '}';
    }

    private boolean isValidBookRequest(Map<String, Object> bookRequest) {
        for (String field : new String[] {"isbn", "title", "publishYear", "price", "genre"}) {
            if (bookRequest.get(field) == null) return false;
        }
        return true;
    }
}
