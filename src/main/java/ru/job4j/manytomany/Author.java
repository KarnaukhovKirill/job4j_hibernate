package ru.job4j.manytomany;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Book> books = new HashSet<>();

    public static Author of(String name) {
        Author author = new Author();
        author.name = name;
        return author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addBooks(Book book) {
        this.books.add(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Author author = (Author) o;
        return id == author.id && Objects.equals(name, author.
                name) && Objects.equals(books, author.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, books);
    }

    @Override
    public String toString() {
        return "Author{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", books=" + books
                + '}';
    }
}
