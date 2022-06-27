/* (C)2022 */
package com.example.demo.model;

import java.util.Map;
import javax.persistence.*;

@Entity
public class Author {
    @Id @GeneratedValue private Long id;
    private String name;
    private String birthday;

    public Author() {}

    public Author(String name) {
        this.name = name;
    }

    public Author(Map<String, Object> authorRequest) {
        if (isValidAuthorRequest(authorRequest)) {
            this.name = authorRequest.get("name").toString();
            this.birthday = authorRequest.get("birthday").toString();
        } else {
            throw new IllegalArgumentException("Missing one or more fields");
        }
    }

    @Override
    public String toString() {
        return "Author{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + ", birthday='"
                + birthday
                + '\''
                + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Author(String name, String birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    private boolean isValidAuthorRequest(Map<String, Object> authorRequest) {
        for (String field : new String[] {"name", "birthday"}) {
            if (authorRequest.get(field) == null) return false;
        }
        return true;
    }
}
