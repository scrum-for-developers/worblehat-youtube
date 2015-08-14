package de.codecentric.psd.worblehat.web.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;

public class BookListControllerTest {

	private BookRepository mockRepository;

	private BookListController bookListController;

	private ModelMap modelMap;

	@Before
	public void setUp() {
		modelMap = new ModelMap();
		mockRepository = mock(BookRepository.class);
		bookListController = new BookListController(mockRepository);
	}

	@Test
	public void shouldRedirectToBooklist() throws Exception {
		String viewToShow = bookListController.setupForm(modelMap);
		assertThat(viewToShow, is("bookList"));
	}

	@Test
	public void shouldShowAllBooks() {
		Book testBook = new Book("Test", "Test", "Test", "Test", 2010);
		when(mockRepository.findAllBooks()).thenReturn(Collections.singletonList(testBook));

		bookListController.setupForm(modelMap);

		@SuppressWarnings("unchecked") // we know that modelMap values are of type List<Book>
		List<Book> books = (List<Book>) modelMap.get("books");
		assertThat(books.size(), is(1));
		assertThat(books.get(0), is(equalTo(testBook)));
	}
}
