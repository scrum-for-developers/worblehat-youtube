package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping
    public String setupForm(ModelMap modelMap) {
        List<Book> books = bookService.findAllBooks();
        modelMap.addAttribute("books", books);
        Map<String, String> covers = getCoverURLsForBooks(books.stream().map(Book::getIsbn).collect(Collectors.toList()));
        modelMap.addAttribute("covers", covers);
        return "bookList";
    }

    protected Map<String, String> getCoverURLsForBooks(List<String> isbns) {
        return isbns.stream()
            .collect(
            Collectors.toMap(
                isbn -> isbn,
                isbn -> "http://covers.openlibrary.org/b/isbn/" +
                    isbn.trim().replace("-", "").replace(" ", "")
                    + "-S.jpg",
                (x1, x2) -> x1
                    ));
    }

}
