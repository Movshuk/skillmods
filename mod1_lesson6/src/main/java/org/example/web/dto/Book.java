package org.example.web.dto;

public class Book {
    private Integer id;
    private String author;
    private String title;
    private Integer size;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Book() {
    }

    public Book(String author, String title, Integer size) {
        this.author = author;
        this.title = title;
        this.size = size;
    }

    @Override
    public String toString() {
        return "Book{" +
            "id=" + id +
            ", author='" + author + '\'' +
            ", title='" + title + '\'' +
            ", size=" + size +
            '}';
    }
}
