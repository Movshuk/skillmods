package org.example.app.services;

import java.util.Objects;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retreiveAll();
    }

    public void saveBook(Book book) {
        bookRepo.store(book);
    }

    public boolean removeBookById(Integer bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }

    // валидация, заполнено хотя бы одно поле
    public boolean isRequestDataValid(Book book) {
        return !book.getAuthor().isEmpty()
            || !book.getTitle().isEmpty()
            || Objects.nonNull(book.getSize());
    }

    public void  initBooks() {
        bookRepo.init();
    }
}
