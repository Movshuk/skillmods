package org.example.app.services;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    // Все объекты в хранятся в репозитории по ключу
    private final Map<Integer, Book> repoMap = new HashMap<>();

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repoMap.values());
    }

    @Override
    public void store(Book book) {
        book.setId(book.hashCode());
        logger.info("store new book: " + book);

        repoMap.put(book.hashCode(), book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        // проверка, если объект существует, то удаляем
        if (isExist(bookIdToRemove)) {
            repoMap.remove(bookIdToRemove);
            logger.info("Remove book completed, id book: " + bookIdToRemove);
            return true;
        }
        logger.info("Book with id: " + bookIdToRemove + " not found. Can't remove.");
        return false;
    }

    // проверка, что объект существует
    @Override
    public boolean isExist(Integer bookId) {
        return repoMap.containsKey(bookId);
    }

    // первичная инициализация объектов
    @Override
    public void init() {
        List<Book> books = new ArrayList<>();

        if (repoMap.isEmpty()) {

            books.add(new Book("Pushkin", "Onegin", 1000));
            books.add(new Book("Tolstoy", "War and Peace", 1500));
            books.add(new Book("Turgenev", "Otsy and deti", 300));
            books.add(new Book("Esenin", "Rus", 40));
            books.add(new Book("Efremod", "Hour of Bull", 800));

            books.forEach(
                book -> {
                    book.setId(book.hashCode());
                    repoMap.put(book.getId(), book);
                }
            );

            logger.info("Collection books is done.");
        }
    }
}
