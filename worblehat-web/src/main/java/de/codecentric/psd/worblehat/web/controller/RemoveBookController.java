package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import java.util.Set;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

@RequestMapping("/removeBook")
@RequiredArgsConstructor
public class RemoveBookController {
    private static final String REDIRECT_TO_BOOK_LIST = "redirect:bookList";

    @NonNull private final BookService bookService;

//    @GetMapping
    public String removeBook(@RequestParam String isbn) {
        bookService.removeBook(isbn);
        return REDIRECT_TO_BOOK_LIST;
    }
}
