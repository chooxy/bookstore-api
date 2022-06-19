/* (C)2022 */
package com.example.demo.model;

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
}
