package de.codecentric.psd.worblehat.web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.command.ReturnAllBooksFormData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

public class ReturnAllBookControllerTest {

	private ReturnAllBookController returnAllBookController;

	private ModelMap modelMap;

	private BookService bookService;

	private BindingResult mockBindingResult;

	@Before
	public void setUp() {
		mockBindingResult = mock(BindingResult.class);
		bookService = mock(BookService.class);
		returnAllBookController = new ReturnAllBookController(bookService);
		modelMap = new ModelMap();
	}

	@Test
	public void shouldPrepareView() {
		returnAllBookController.prepareView(modelMap);
		ReturnAllBooksFormData object = (ReturnAllBooksFormData) modelMap
				.get("returnAllBookFormData");
		assertThat(object, not(nullValue()));
	}

	@Test
	public void shouldReturnAllBook() {
		when(mockBindingResult.hasErrors()).thenReturn(false);
		ReturnAllBooksFormData formData = new ReturnAllBooksFormData();
		formData.setEmailAddress("email@email.de");
		String page = returnAllBookController.returnAllBooks(
				formData, mockBindingResult);
		assertThat(page, is("/home"));
		verify(mockBindingResult).hasErrors();
		verify(bookService).returnAllBooksByBorrower("email@email.de");
	}

	@Test
	public void shouldNotReturnAllBook() {
		when(mockBindingResult.hasErrors()).thenReturn(true);
		String page = returnAllBookController.returnAllBooks(
				new ReturnAllBooksFormData(), mockBindingResult);
		assertThat(page, is("/returnAllBooks"));
		verify(mockBindingResult).hasErrors();
	}
}
