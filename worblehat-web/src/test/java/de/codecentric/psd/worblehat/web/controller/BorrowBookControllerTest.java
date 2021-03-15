package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.domain.Borrowing;
import de.codecentric.psd.worblehat.web.formdata.BorrowBookFormData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BorrowBookControllerTest {

  private BookService bookService;

  private BorrowBookController borrowBookController;

  private BindingResult bindingResult;

  private BorrowBookFormData bookBorrowFormData;

  private static final Book TEST_BOOK = new Book("title", "author", "edition", "isbn", 2016);

  public static final String BORROWER_EMAIL = "someone@codecentric.de";

  @BeforeEach
  void setUp() {
    bookService = mock(BookService.class);
    bindingResult = new MapBindingResult(new HashMap<>(), "");
    bookBorrowFormData = new BorrowBookFormData();
    borrowBookController = new BorrowBookController(bookService);
  }

  @Test
  void shouldSetupForm() {
    ModelMap modelMap = new ModelMap();

    borrowBookController.setupForm(modelMap);

    assertThat(modelMap.get("borrowFormData"), is(not(nullValue())));
  }

  @Test
  void shouldNavigateToBorrowWhenResultHasErrors() {
    bindingResult.addError(new ObjectError("", ""));

    String navigateTo = borrowBookController.processSubmit(bookBorrowFormData, bindingResult);

    assertThat(navigateTo, is("borrow"));
  }

  @Test
  void shouldRejectBorrowingIfBookDoesNotExist() {
    when(bookService.findBooksByIsbn(TEST_BOOK.getIsbn())).thenReturn(null);

    String navigateTo = borrowBookController.processSubmit(bookBorrowFormData, bindingResult);

    assertThat(bindingResult.hasFieldErrors("isbn"), is(true));
    assertThat(navigateTo, is("borrow"));
  }

  @Test
  void shouldRejectAlreadyBorrowedBooks() {
    bookBorrowFormData.setEmail(BORROWER_EMAIL);
    bookBorrowFormData.setIsbn(TEST_BOOK.getIsbn());
    when(bookService.findBooksByIsbn(TEST_BOOK.getIsbn()))
        .thenReturn(Collections.singleton(TEST_BOOK));
    String navigateTo = borrowBookController.processSubmit(bookBorrowFormData, bindingResult);

    assertThat(bindingResult.hasFieldErrors("isbn"), is(true));
    assertThat(bindingResult.getFieldError("isbn").getCode(), is("noBorrowableBooks"));
    assertThat(navigateTo, is("borrow"));
  }

  @Test
  void shouldNavigateHomeOnSuccess() {
    bookBorrowFormData.setEmail(BORROWER_EMAIL);
    bookBorrowFormData.setIsbn(TEST_BOOK.getIsbn());
    when(bookService.findBooksByIsbn(TEST_BOOK.getIsbn()))
        .thenReturn(Collections.singleton(TEST_BOOK));
    when(bookService.borrowBook(any(), any()))
        .thenReturn(Optional.of(new Borrowing(TEST_BOOK, BORROWER_EMAIL, LocalDate.now())));

    String navigateTo = borrowBookController.processSubmit(bookBorrowFormData, bindingResult);
    verify(bookService).borrowBook(TEST_BOOK.getIsbn(), BORROWER_EMAIL);
    assertThat(navigateTo, is("home"));
  }

  @Test
  void shouldNavigateToHomeOnErrors() {
    String navigateTo =
        borrowBookController.handleErrors(new Exception(), new MockHttpServletRequest());

    assertThat(navigateTo, is("home"));
  }
}
