package de.codecentric.psd.worblehat.web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.command.ReturnAllBooksFormData;

public class ReturnAllBookControllerTest {

	@InjectMocks
	private ReturnAllBookController returnAllBookController;

	private ModelMap modelMap;

	@Mock
	private BookService bookService;

	@Mock
	private BindingResult mockBindingResult;

	@Before
	public void setUp() {
		returnAllBookController = new ReturnAllBookController();
		MockitoAnnotations.initMocks(this);
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
		String page = returnAllBookController.returnAllBooks(modelMap,
				formData, mockBindingResult);
		assertThat(page, is("/home"));
		verify(mockBindingResult).hasErrors();
		verify(bookService).returnAllBooksByBorrower("email@email.de");
	}

	@Test
	public void shouldNotReturnAllBook() {
		when(mockBindingResult.hasErrors()).thenReturn(true);
		String page = returnAllBookController.returnAllBooks(modelMap,
				new ReturnAllBooksFormData(), mockBindingResult);
		assertThat(page, is("/returnAllBooks"));
		verify(mockBindingResult).hasErrors();
	}
}
