package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Controller class for the book table result.
 */
@Controller
@RequestMapping("/bookList")
@RequiredArgsConstructor
public class BookListController {

    @NonNull
    private final BookService bookService;

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(ModelMap modelMap) {
        List<Book> books = bookService.findAllBooks();
        modelMap.addAttribute("books", books);
        return "bookList";
    }

}
