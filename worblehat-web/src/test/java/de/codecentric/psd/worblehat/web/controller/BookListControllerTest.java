package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.mockito.Mockito.when;

@MockitoSettings
class BookListControllerTest {

    @InjectMocks
    private BookListController bookListController;

    @Mock
    private BookService bookService;

    private static final Book TEST_BOOK = new Book("title", "author", "edition", "isbn", 2016);
    private static final Book TEST_BOOK2 = new Book("title2", "author2", "edition2", "isbn2", 2018);

    private ModelMap modelMap;

    @BeforeEach
    void setUp() {
        modelMap = new ModelMap();
    }

    @Test
    void shouldNavigateToBookList() {
        String url = bookListController.setupForm(modelMap);
        assertThat(url, is("bookList"));
    }

    @Test
    void shouldContainBooks() {
        List<Book> bookList = List.of(TEST_BOOK);
        when(bookService.findAllBooks()).thenReturn(bookList);
        bookListController.setupForm(modelMap);
        List<Book> actualBooks = (List<Book>) modelMap.get("books");
        assertThat(actualBooks, is(bookList));
    }

    @Test
    void shouldContainCovers() {
        List<Book> bookList = List.of(TEST_BOOK);
        when(bookService.findAllBooks()).thenReturn(bookList);
        bookListController.setupForm(modelMap);
        Map<String, String> actualCovers = (Map<String, String>) modelMap.get("covers");
        assertThat(actualCovers.values(), hasSize(1));
    }

    @Test
    void shouldBuildCoverMapForBooks() {
        List<Book> testBooks = List.of(TEST_BOOK, TEST_BOOK2);
        Map<String, String> coverURLsForBooks = bookListController.getCoverURLsForBooks(testBooks);
        assertThat(coverURLsForBooks.keySet(), hasSize(2));
        assertThat(coverURLsForBooks.values(), containsInAnyOrder("http://covers.openlibrary.org/b/isbn/" + TEST_BOOK.getIsbn() + "-S.jpg", "http://covers.openlibrary.org/b/isbn/" + TEST_BOOK2.getIsbn() + "-S.jpg"));
    }
}
