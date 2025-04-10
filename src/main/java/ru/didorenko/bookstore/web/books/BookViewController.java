package ru.didorenko.bookstore.web.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.didorenko.bookstore.service.BookService;

@Controller
public class BookViewController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public String showBooksPage(Model model) {
        model.addAttribute("books", bookService.getBooks(PageRequest.of(0, 10)));
        return "books";
    }
}