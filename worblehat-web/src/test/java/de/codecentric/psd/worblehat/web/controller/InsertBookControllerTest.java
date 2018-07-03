package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.InsertBookFormData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InsertBookControllerTest {

    private InsertBookController insertBookController;

    private BookService bookService;

    private InsertBookFormData insertBookFormData;

    private BindingResult bindingResult;

    private static final Book TEST_BOOK = new Book("title", "author", "edition", "isbn", 2016);

    @BeforeEach
    void setUp() {
        bookService = mock(BookService.class);
        insertBookController = new InsertBookController(bookService);
        insertBookFormData = new InsertBookFormData();
        bindingResult = new MapBindingResult(new HashMap<>(), "");
    }

    @Test
    void shouldSetupForm() {
        ModelMap modelMap = new ModelMap();

        insertBookController.setupForm(modelMap);

        assertThat(modelMap.get("insertBookFormData"), is(not(nullValue())));
    }

    @Test
    void shouldRejectErrors() {
        bindingResult.addError(new ObjectError("", ""));

        String navigateTo = insertBookController.processSubmit(insertBookFormData, bindingResult);

        assertThat(navigateTo, is("insertBooks"));
    }

    @Test
    void shouldCreateNewCopyOfExistingBook() {
        setupFormData();
        when(bookService.bookExists(TEST_BOOK.getIsbn())).thenReturn(true);
        when(bookService.createBook(any(), any(), any(), any(), anyInt(), any())).thenReturn(Optional.of(TEST_BOOK));

        String navigateTo = insertBookController.processSubmit(insertBookFormData, bindingResult);

        verifyBookIsCreated();
        assertThat(navigateTo, is("redirect:bookList"));
    }

    @Test
    void shouldCreateBookAndNavigateToBookList() {
        setupFormData();
        when(bookService.bookExists(TEST_BOOK.getIsbn())).thenReturn(false);
        when(bookService.createBook(any(), any(), any(), any(), anyInt(), any())).thenReturn(Optional.of(TEST_BOOK));

        String navigateTo = insertBookController.processSubmit(insertBookFormData, bindingResult);

        verifyBookIsCreated();
        assertThat(navigateTo, is("redirect:bookList"));
    }

    private void verifyBookIsCreated() {
        verify(bookService).createBook(TEST_BOOK.getTitle(), TEST_BOOK.getAuthor(),
                TEST_BOOK.getEdition(), TEST_BOOK.getIsbn(), TEST_BOOK.getYearOfPublication(),
                TEST_BOOK.getDescription());
    }

    private void setupFormData() {
        insertBookFormData.setTitle(TEST_BOOK.getTitle());
        insertBookFormData.setAuthor(TEST_BOOK.getAuthor());
        insertBookFormData.setEdition(TEST_BOOK.getEdition());
        insertBookFormData.setIsbn(TEST_BOOK.getIsbn());
        insertBookFormData.setYearOfPublication(String.valueOf(TEST_BOOK.getYearOfPublication()));
    }
}
