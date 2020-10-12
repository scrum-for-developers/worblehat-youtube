package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.ReturnAllBooksFormData;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/** Controller class for the */
@Controller
@RequestMapping("/returnAllBooks")
@RequiredArgsConstructor
public class ReturnAllBooksController {

  @NonNull private final BookService bookService;

  @GetMapping
  public void prepareView(ModelMap modelMap) {
    modelMap.put("returnAllBookFormData", new ReturnAllBooksFormData());
  }

  @PostMapping
  public String returnAllBooks(
      @ModelAttribute("returnAllBookFormData") @Valid ReturnAllBooksFormData formData,
      BindingResult result) {
    if (result.hasErrors()) {
      return "returnAllBooks";
    } else {
      bookService.returnAllBooksByBorrower(formData.getEmailAddress());
      return "home";
    }
  }
}
