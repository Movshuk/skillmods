package org.example.web.controllers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        bookService.initBooks();
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        //return "book_shelf";
        return "book_shelf";
    }

    /*
    API сохранение объекта
    должно быть заполнено хотя бы одно поле из author, title, size
     */
    @PostMapping("/save")
    public String saveBook(Book book, Model model) {
        // валидация входящих данных на соответствие требованию
        if (bookService.isRequestDataValid(book)) {
            bookService.saveBook(book);
            logger.info("Current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
        logger.info("No valid data: one or more fields are empty");
        return "redirect:/books/shelf";
    }

    /*
    API удаление объекта по id
    если id не существует, остаемся на странице
     */
    @PostMapping("/remove")
    public String removeBook(@RequestParam(value = "bookIdToRemove") Integer bookIdToRemove) {
        bookService.removeBookById(bookIdToRemove);
        return "redirect:/books/shelf";
    }

    /*
    API удаление объекта по входящему регулярному выражение
    (?<author>(?<=author=')([Turgenev].+?)(?='|})).+(?<title>(?<=title=')([Onegin and Onegin]+?)(?='|})).+(?<size>(?<=size=)([300]+?)(?=}))
    с группировкой поиска в строке
     */
    @PostMapping("/remove-by-regex")
    public String removeBookByRegex(@RequestParam(value = "requestString") String requestString) {
        List<Book> books = bookService.getAllBooks();

        try {
            Pattern patternA = Pattern.compile(requestString);
            books.forEach(
                book -> {
                    Matcher matcherA = patternA.matcher(book.toString());
                    if (matcherA.find()) {
                        bookService.removeBookById(book.getId());
                    }
                }
            );
        } catch (Exception ex) {
            logger.info("Book is not removed. Something wrong: " + ex.getMessage());
            return "redirect:/books/shelf";
        }

        return "redirect:/books/shelf";
    }
}
