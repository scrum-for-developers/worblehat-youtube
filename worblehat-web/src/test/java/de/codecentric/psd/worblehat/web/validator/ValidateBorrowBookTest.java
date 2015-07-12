package de.codecentric.psd.worblehat.web.validator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import de.codecentric.psd.worblehat.web.command.BookBorrowFormData;

public class ValidateBorrowBookTest {

	private static final String INVALID_ISBN = "978-3492285100-22";
	private static final String VALID_ISBN = "90-70002-34-5";
	private static final String VALID_EMAIL = "valid.user@worblehat.com";

	private ValidateBorrowBook validateAddBook;

	private BookBorrowFormData cmd = new BookBorrowFormData(VALID_ISBN, VALID_EMAIL);

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.validateAddBook = new ValidateBorrowBook();
	}

	@Test
	public void shouldFailForEmptyISBN() {
		cmd.setIsbn("         ");
		validateForOneError();
	}

	@Test
	public void shouldFailForNullISBN() {
		cmd.setIsbn(null);
		validateForOneError();
	}

	@Test
	public void shouldValidateISBN10() {
		String isbn13 = "90-70002-34-5";
		cmd.setIsbn(isbn13);
		Errors errors = new BindException(cmd, "bookBorrowCmd");
		validateAddBook.validate(cmd, errors);
		Object value = errors.getFieldValue("isbn");
		assertThat(errors.getErrorCount(), is(0));
		assertEquals(isbn13, value);
	}

	@Test
	public void shouldFailForInvalidISBN() {
		String isbn13 = INVALID_ISBN;
		cmd.setIsbn(isbn13);
		validateForOneError();
	}

	@Test
	public void shouldFailForEmptyEmail() {
		cmd.setEmail("         ");
		validateForOneError();
	}

	@Test
	public void shouldFailForInvalidEmail() {
		cmd.setEmail("Hello World");
		validateForOneError();
	}

	private void validateForOneError() {
		Errors errors = new BindException(cmd, "cmdBookdData");
		validateAddBook.validate(cmd, errors);
		assertThat(errors.getErrorCount(), is(1));
	}

}
