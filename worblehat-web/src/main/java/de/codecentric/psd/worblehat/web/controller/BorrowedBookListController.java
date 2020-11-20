package de.codecentric.psd.worblehat.web.controller;


import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.domain.Borrowing;
import de.codecentric.psd.worblehat.web.formdata.ReturnAllBooksFormData;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/borrowedBookList")
@Controller
@RequiredArgsConstructor
public class BorrowedBookListController {

    //TODO RÜckgabe wert nicht vergessen !!!!!!

    @NonNull private final BookService bookService;

    @GetMapping
    public void get(ModelMap modelMap) {
        modelMap.put("returnAllBookFormData", new ReturnAllBooksFormData());
    }

    @PostMapping
    public String findBorrowedBooksByEmailAddress(@ModelAttribute("returnAllBookFormData") @Valid ReturnAllBooksFormData returnAllBooksFormData, BindingResult result, ModelMap modelMap) {
        List<Borrowing> borrowings = bookService.findAllBorrowingsByEmailAddress(returnAllBooksFormData.getEmailAddress());

        modelMap.put("borrowings", borrowings);

        return "borrowedBookList";
    }
}
