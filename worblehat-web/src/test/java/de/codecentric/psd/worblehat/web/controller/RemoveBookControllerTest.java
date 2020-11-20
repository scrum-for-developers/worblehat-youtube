package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class RemoveBookControllerTest {

    private RemoveBookController removeBookController;

    //@Mock
    BookService bookService;

    @BeforeEach
    public void setup() {
        bookService = mock(BookService.class);
        removeBookController = new RemoveBookController(bookService);
    }

    @Test
    public void shouldRemoveBook() {
        String result = removeBookController.removeBook("anIsbn");
        verify(bookService).removeBook("anIsbn");
        assertThat(result, is("redirect:bookList"));
    }
}
