package de.codecentric.psd.worblehat.web.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import de.codecentric.psd.worblehat.web.command.ReturnAllBooksFormData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;


public class ValidateReturnAllBooksTest {

	private static final String VALID_EMAIL = "valid.user@worblehat.com";
	private ValidateReturnAllBooks validateReturnAllBooks;
	private ReturnAllBooksFormData cmd;

	@Before
	public void setUp(){
		validateReturnAllBooks = new ValidateReturnAllBooks();
		cmd = new ReturnAllBooksFormData(VALID_EMAIL);
	}

	@Test
	public void shouldWorkForThisValidator(){
		boolean supports = validateReturnAllBooks.supports(ReturnAllBooksFormData.class);
		assertTrue(supports);
	}

	@Test
	public void shouldValidateValidEmail(){
		Errors errors = new BindException(cmd, "returnAllBookFormData");
		validateReturnAllBooks.validate(cmd, errors);
		assertEquals(0, errors.getErrorCount());
	}

	@Test
	public void shouldFailOnEmptyEmail(){
		Errors errors = new BindException(cmd, "returnAllBookFormData");
		cmd.setEmailAddress(" ");
		validateReturnAllBooks.validate(cmd, errors);
		assertEquals(1, errors.getErrorCount());
		String code = errors.getFieldError("emailAddress").getCode();
		assertEquals("empty", code);
	}

	@Test
	public void shouldFailOnInvalidEmail(){
		Errors errors = new BindException(cmd, "returnAllBookFormData");
		cmd.setEmailAddress("aa.de");
		validateReturnAllBooks.validate(cmd, errors);
		assertEquals(1, errors.getErrorCount());
		String code = errors.getFieldError("emailAddress").getCode();
		assertEquals("notvalid", code);
	}

}
