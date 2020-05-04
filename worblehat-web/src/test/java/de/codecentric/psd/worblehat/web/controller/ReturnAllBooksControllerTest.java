package de.codecentric.psd.worblehat.web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.ReturnAllBooksFormData;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

class ReturnAllBooksControllerTest {

  private ReturnAllBooksController returnAllBooksController;

  private BookService bookService;

  private ReturnAllBooksFormData returnAllBooksFormData;

  private BindingResult bindingResult;

  @BeforeEach
  void setUp() throws Exception {
    bookService = mock(BookService.class);
    returnAllBooksController = new ReturnAllBooksController(bookService);
    returnAllBooksFormData = new ReturnAllBooksFormData();
    bindingResult = new MapBindingResult(new HashMap<>(), "");
  }

  @Test
  void shouldSetupForm() throws Exception {
    ModelMap modelMap = new ModelMap();

    returnAllBooksController.prepareView(modelMap);

    assertThat(modelMap.get("returnAllBookFormData"), is(not(nullValue())));
  }

  @Test
  void shouldRejectErrors() throws Exception {
    bindingResult.addError(new ObjectError("", ""));

    String navigateTo =
        returnAllBooksController.returnAllBooks(returnAllBooksFormData, bindingResult);

    assertThat(navigateTo, is("returnAllBooks"));
  }

  @Test
  void shouldReturnAllBooksAndNavigateHome() throws Exception {
    String borrower = "someone@codecentric.de";
    returnAllBooksFormData.setEmailAddress(borrower);

    String navigateTo =
        returnAllBooksController.returnAllBooks(returnAllBooksFormData, bindingResult);

    verify(bookService).returnAllBooksByBorrower(borrower);
    assertThat(navigateTo, is("home"));
  }
}
