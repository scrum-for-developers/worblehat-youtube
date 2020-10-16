package de.codecentric.psd.worblehat.web.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableSet;
import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.ui.ModelMap;

@MockitoSettings
public class BookDetailsControllerTest {

  @InjectMocks private BookDetailsController bookDetailsController;

  @Mock private BookService bookService;

  private ModelMap modelMap;

  public static final String ISBN = "123456789X";
  private static final Book TEST_BOOK = new Book("title", "author", "edition", "isbn", 2016);

  @BeforeEach
  void setUp() {
    modelMap = new ModelMap();
    when(bookService.findBooksByIsbn(ISBN)).thenReturn(ImmutableSet.of(TEST_BOOK));
  }

  @Test
  void shouldNavigateToBookDetails() {
    String url = bookDetailsController.setupForm(modelMap, ISBN);
    assertThat(url, containsString("bookDetails"));
  }

  @Test
  void shouldContainBookDetails() {
    bookDetailsController.setupForm(modelMap, ISBN);
    Book actualBook = (Book) modelMap.get("book");
    assertThat(actualBook, is(TEST_BOOK));
  }

}
