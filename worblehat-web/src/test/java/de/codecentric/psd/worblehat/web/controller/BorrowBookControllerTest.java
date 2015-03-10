package de.codecentric.psd.worblehat.web.controller;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookAlreadyBorrowedException;
import de.codecentric.psd.worblehat.domain.BookRepository;
import de.codecentric.psd.worblehat.domain.NoBookBorrowableException;
import de.codecentric.psd.worblehat.web.command.BookBorrowFormData;

public class BorrowBookControllerTest {

	private BindingResult mockBindingResult;

	private BookRepository mockRepository;

	private ModelMap modelMap;

	private StandarBorrowBookController booksController;

	@Before
	public void setUp() {
		mockBindingResult = mock(BindingResult.class);
		mockRepository = mock(BookRepository.class);
		booksController = new StandarBorrowBookController(mockRepository);

		modelMap = new ModelMap();
	}

	@Test
	public void shouldPrepareForm() {
		ModelMap model = new ModelMap();
		booksController.setupForm(model);
		Object object = model.get("borrowFormData");
		assertNotNull(object);
	}

	@Test
	public void shouldReturnErrorValidatorReturnsErrors()
			throws NoBookBorrowableException, BookAlreadyBorrowedException {
		BookBorrowFormData borrowFormData = new BookBorrowFormData(
				"90-70002-34-5", "test@codecentric.de");
		when(mockBindingResult.hasErrors()).thenReturn(true);

		String path = booksController.processSubmit(modelMap,
				borrowFormData, mockBindingResult);

		assertThat(path, is("/borrow"));
		assertEquals(borrowFormData, modelMap.get("borrowFormData"));
	}

	@Test
	public void shouldBorrowBook() throws NoBookBorrowableException,
			BookAlreadyBorrowedException {
		BookBorrowFormData borrowFormData = new BookBorrowFormData(
				"90-70002-34-5", "test@codecentric.de");

		when(mockBindingResult.hasErrors()).thenReturn(false);
		Book testBook = new Book("Test", "Test", "Test", "Test", 2010);
		when(mockRepository.findBorrowableBook("90-70002-34-5")).thenReturn(
				testBook);

		String path = booksController.processSubmit(modelMap,
				borrowFormData, mockBindingResult);

		assertThat(path, is("/home"));
		assertThat(testBook.getCurrentBorrowing().getBorrowerEmailAddress(), is("test@codecentric.de"));

		verify(mockBindingResult).hasErrors();
	}

	@Test
	public void shouldReturnErrorIfNoBookIsBorrowable()
			throws NoBookBorrowableException, BookAlreadyBorrowedException {
		BookBorrowFormData borrowFormData = new BookBorrowFormData(
				"90-70002-34-5", "test@codecentric.de");

		when(mockRepository.findBorrowableBook("90-70002-34-5")).thenThrow(
				new NoBookBorrowableException("90-70002-34-5"));

		String path = booksController.processSubmit(modelMap,
				borrowFormData, mockBindingResult);

		assertThat(path, is("/borrow"));
		assertEquals(borrowFormData, modelMap.get("borrowFormData"));
		verify(mockBindingResult).rejectValue("isbn", "notBorrowable");
	}
}
