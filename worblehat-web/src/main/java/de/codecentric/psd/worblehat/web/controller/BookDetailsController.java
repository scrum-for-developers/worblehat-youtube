package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BookDetailsController {

    private BookService bookService;

    @Autowired
    public BookDetailsController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(path = "/book/{bookId}", method = RequestMethod.GET)
    public String setupForm(ModelMap modelMap, @PathVariable long bookId) {
        Book book = bookService.get(bookId);
        String coverUrl = "http://covers.openlibrary.org/b/isbn/" + book.getIsbn() + "-M.jpg";
        modelMap.addAttribute("book", book);
        modelMap.addAttribute("coverUrl", coverUrl);

        return "bookDetails";
    }

}
