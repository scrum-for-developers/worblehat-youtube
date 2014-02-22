package de.codecentric.psd.worblehat.web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;

import de.codecentric.psd.worblehat.domain.BookRepository;

public class BookListControllerTest {

	@Mock
	private ModelMap mockModelMap;

	@Mock
	private BookRepository mockRepository;

	@InjectMocks
	BookListController bookListController;

	@Before
	public void setUp() {
		bookListController = new BookListController();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldShowAllBooks() {
		String bookList = bookListController.setupForm(mockModelMap);

		verify(mockModelMap).addAttribute(anyString(), any());
		verify(mockRepository).findAllBooks();
		assertThat(bookList, is("bookList"));
	}
}
