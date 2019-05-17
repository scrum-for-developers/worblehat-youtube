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
import java.util.Map;
import java.util.stream.Collectors;

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
        Map<String, String> covers = getCoverURLsForBooks(books);
        modelMap.addAttribute("covers", covers);
        return "bookList";
    }

    protected Map<String, String> getCoverURLsForBooks(List<Book> books) {
        return books.stream().collect(
            Collectors.toMap(
                Book::getIsbn, book -> "http://covers.openlibrary.org/b/isbn/" + book.getIsbn() + "-S.jpg"));
    }

}
