package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import java.util.Set;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bookDetails")
@RequiredArgsConstructor
public class BookDetailsController {
  private static final String BOOK_DETAILS = "bookDetails";

  private static final String REDIRECT_TO_BOOK_LIST = "redirect:bookList";

  @NonNull private final BookService bookService;

  @RequestMapping(method = RequestMethod.GET)
  public String setupForm(ModelMap modelMap, @RequestParam String isbn) {
    Set<Book> books = bookService.findBooksByIsbn(isbn);
    if (books.isEmpty()) {
      return REDIRECT_TO_BOOK_LIST;
    }
    Book book = books.iterator().next();
    modelMap.addAttribute("book", book);
    return BOOK_DETAILS;
  }
}
