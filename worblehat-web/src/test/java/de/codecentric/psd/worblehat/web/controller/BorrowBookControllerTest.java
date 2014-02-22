package de.codecentric.psd.worblehat.web.controller;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookAlreadyBorrowedException;
import de.codecentric.psd.worblehat.domain.BookRepository;
import de.codecentric.psd.worblehat.domain.NoBookBorrowableException;
import de.codecentric.psd.worblehat.web.command.BookBorrowFormData;
import de.codecentric.psd.worblehat.web.validator.ValidateBorrowBook;

public class BorrowBookControllerTest {

	@Mock
	private BindingResult mockBindingResult;

	@Mock
	private BookRepository mockRepository;

	@Mock
	private ModelMap mockModelMap;

	@Mock
	private Book bookMock;

	@Mock
	private ValidateBorrowBook validatorMock;

	@InjectMocks
	private StandarBorrowBookController booksController;

	@Before
	public void setUp() {
		booksController = new StandarBorrowBookController();
		MockitoAnnotations.initMocks(this);
		booksController.setValidator(validatorMock);
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

		String path = booksController.processSubmit(mockModelMap,
				borrowFormData, mockBindingResult);
		verify(mockModelMap).put("borrowFormData", borrowFormData);
		verify(bookMock, never()).borrow("test@codecentric.de");
		assertThat(path, is("/borrow"));
	}

	@Test
	public void shouldBorrowBook() throws NoBookBorrowableException,
			BookAlreadyBorrowedException {
		BookBorrowFormData borrowFormData = new BookBorrowFormData(
				"90-70002-34-5", "test@codecentric.de");

		when(mockBindingResult.hasErrors()).thenReturn(false);
		when(mockRepository.findBorrowableBook("90-70002-34-5")).thenReturn(
				bookMock);

		String path = booksController.processSubmit(mockModelMap,
				borrowFormData, mockBindingResult);

		verify(bookMock).borrow("test@codecentric.de");
		assertThat(path, is("/home"));

		verify(mockBindingResult).hasErrors();
		verifyNoMoreInteractions(mockBindingResult);
	}

	@Test
	public void shouldReturnErrorIfNoBookIsBorrowable()
			throws NoBookBorrowableException, BookAlreadyBorrowedException {
		BookBorrowFormData borrowFormData = new BookBorrowFormData(
				"90-70002-34-5", "test@codecentric.de");

		when(mockRepository.findBorrowableBook("90-70002-34-5")).thenThrow(
				new NoBookBorrowableException("90-70002-34-5"));

		String path = booksController.processSubmit(mockModelMap,
				borrowFormData, mockBindingResult);

		verify(mockModelMap).put("borrowFormData", borrowFormData);
		verify(bookMock, never()).borrow("test@codecentric.de");
		assertThat(path, is("/borrow"));
		verify(mockBindingResult).rejectValue("isbn", "notBorrowable");
	}
}
