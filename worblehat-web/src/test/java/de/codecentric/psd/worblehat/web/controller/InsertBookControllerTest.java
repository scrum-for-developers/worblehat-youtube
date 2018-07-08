package de.codecentric.psd.worblehat.web.controller;

import java.util.HashMap;
import java.util.Optional;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookParameter;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.InsertBookFormData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InsertBookControllerTest {

    private InsertBookController insertBookController;

    private BookService bookService;

    private InsertBookFormData insertBookFormData;

    private BindingResult bindingResult;

    private static final Book TEST_BOOK = new Book("title", "author", "edition", "isbn", 2016);

    @Before
    public void setUp() {
        bookService = mock(BookService.class);
        insertBookController = new InsertBookController(bookService);
        insertBookFormData = new InsertBookFormData();
        bindingResult = new MapBindingResult(new HashMap<>(), "");
    }

    @Test
    public void shouldSetupForm() {
        ModelMap modelMap = new ModelMap();

        insertBookController.setupForm(modelMap);

        assertThat(modelMap.get("insertBookFormData"), is(not(nullValue())));
    }

    @Test
    public void shouldRejectErrors() {
        bindingResult.addError(new ObjectError("", ""));

        String navigateTo = insertBookController.processSubmit(insertBookFormData, bindingResult);

        assertThat(navigateTo, is("insertBooks"));
    }

    @Test
    public void shouldCreateNewCopyOfExistingBook() {
        setupFormData();
        when(bookService.bookExists(TEST_BOOK.getIsbn())).thenReturn(true);
        when(bookService.createBook(any())).thenReturn(Optional.of(TEST_BOOK));

        String navigateTo = insertBookController.processSubmit(insertBookFormData, bindingResult);

        verifyBookIsCreated();
        assertThat(navigateTo, is("redirect:bookList"));
    }

    @Test
    public void shouldCreateBookAndNavigateToBookList() {
        setupFormData();
        when(bookService.bookExists(TEST_BOOK.getIsbn())).thenReturn(false);
        when(bookService.createBook(any())).thenReturn(Optional.of(TEST_BOOK));

        String navigateTo = insertBookController.processSubmit(insertBookFormData, bindingResult);

        verifyBookIsCreated();
        assertThat(navigateTo, is("redirect:bookList"));
    }

    private void verifyBookIsCreated() {
        ArgumentCaptor<BookParameter> bookArgumentCaptor = ArgumentCaptor.forClass(BookParameter.class);
        verify(bookService).createBook(bookArgumentCaptor.capture());
        BookParameter bookParameter = bookArgumentCaptor.getValue();
        assertThat(bookParameter.getTitle(), is(TEST_BOOK.getTitle()));
        assertThat(bookParameter.getAuthor(), is(TEST_BOOK.getAuthor()));
        assertThat(bookParameter.getEdition(), is(TEST_BOOK.getEdition()));
        assertThat(bookParameter.getIsbn(), is(TEST_BOOK.getIsbn()));
        assertThat(bookParameter.getYearOfPublication(), is(TEST_BOOK.getYearOfPublication()));
    }

    private void setupFormData() {
        insertBookFormData.setTitle(TEST_BOOK.getTitle());
        insertBookFormData.setAuthor(TEST_BOOK.getAuthor());
        insertBookFormData.setEdition(TEST_BOOK.getEdition());
        insertBookFormData.setIsbn(TEST_BOOK.getIsbn());
        insertBookFormData.setYearOfPublication(String.valueOf(TEST_BOOK.getYearOfPublication()));
    }
}
