package de.codecentric.psd.worblehat.web.validator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import de.codecentric.psd.worblehat.web.command.BookDataFormData;
import de.codecentric.psd.worblehat.web.command.ReturnAllBooksFormData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

public class ValidateAddBookTest {

	private ValidateAddBook validateAddBook;
	private BookDataFormData cmd;

	@Before
	public void setup() {
		validateAddBook = new ValidateAddBook();
		cmd = new BookDataFormData();
		cmd.setAuthor("author");
		cmd.setEdition("2");
		cmd.setIsbn("90-70002-34-5");
		cmd.setTitle("title");
		cmd.setYear("2010");
	}

	@Test
	public void shouldSupport() {
		boolean supports = validateAddBook.supports(BookDataFormData.class);
		assertTrue(supports);
	}

	@Test
	public void shouldFailForEmptyTitle() {
		Errors errors = new BindException(cmd, "cmdBookdData");
		cmd.setTitle("");
		validateAddBook.validate((BookDataFormData) cmd, errors);
		assertThat(errors.getErrorCount(), is(1));
	}

	@Test
	public void shouldFailForWhitespaceTitle() {
		Errors errors = new BindException(cmd, "cmdBookdData");
		cmd.setTitle("    ");
		validateAddBook.validate((BookDataFormData) cmd, errors);
		assertThat(errors.getErrorCount(), is(1));
	}

	@Test
	public void shouldFailForNullTitle() {
		Errors errors = new BindException(cmd, "cmdBookdData");
		cmd.setTitle(null);
		validateAddBook.validate((BookDataFormData) cmd, errors);
		assertThat(errors.getErrorCount(), is(1));
	}

	@Test
	public void shouldValidateTitle() {
		String title = "Title";
		cmd.setTitle(title);
		Errors errors = new BindException(cmd, "cmdBookdData");
		validateAddBook.validate((BookDataFormData) cmd, errors);
		assertThat(errors.getErrorCount(), is(0));
		String errorFieldValue = errors.getFieldValue("title").toString();;
		assertThat(errorFieldValue, is(title));
	}

	@Test
	public void shouldFailForEmptyYear() {
		Errors errors = new BindException(cmd, "cmdBookdData");
		cmd.setYear("");
		validateAddBook.validate((BookDataFormData) cmd, errors);
		assertThat(errors.getErrorCount(), is(1));
	}

	@Test
	public void shouldFailForWhiteSpaceYear() {
		Errors errors = new BindException(cmd, "cmdBookdData");
		cmd.setYear("     ");
		validateAddBook.validate((BookDataFormData) cmd, errors);
		assertThat(errors.getErrorCount(), is(1));
	}

	@Test
	public void shouldFailForNullYear() {
		Errors errors = new BindException(cmd, "cmdBookdData");
		cmd.setYear(null);
		validateAddBook.validate((BookDataFormData) cmd, errors);
		assertThat(errors.getErrorCount(), is(1));
	}

	@Test
	public void shouldValidateYear() {
		String year = "2009";
		cmd.setYear(year);
		Errors errors = new BindException(cmd, "cmdBookdData");
		validateAddBook.validate((BookDataFormData) cmd, errors);
		String errorFieldValue = errors.getFieldValue("year").toString();
		assertThat(errors.getErrorCount(), is(0));
		assertThat(errorFieldValue, is(year));
	}

	@Test
	public void shouldFailForCharactersInYear() {
		String year = "200a";
		cmd.setYear(year);
		Errors errors = new BindException(cmd, "cmdBookdData");
		validateAddBook.validate((BookDataFormData) cmd, errors);
		assertEquals(1, errors.getErrorCount());
	}

	@Test
	public void shouldFailFor5DigitInYear() {
		String year = "20099";
		cmd.setYear(year);
		Errors errors = new BindException(cmd, "cmdBookdData");
		validateAddBook.validate((BookDataFormData) cmd, errors);
		assertEquals(1, errors.getErrorCount());
	}

	@Test
	public void shouldFailForEmptyISBN() {
		Errors errors = new BindException(cmd, "cmdBookdData");
		cmd.setIsbn("         ");
		validateAddBook.validate((BookDataFormData) cmd, errors);
		assertEquals(1, errors.getErrorCount());
	}

	@Test
	public void shouldFailForNullISBN() {
		Errors errors = new BindException(cmd, "cmdBookdData");
		cmd.setIsbn(null);
		validateAddBook.validate((BookDataFormData) cmd, errors);
		assertEquals(1, errors.getErrorCount());
	}

	@Test
	public void shouldValidateISBN10() {
		String isbn13 = "90-70002-34-5";
		cmd.setIsbn(isbn13);
		Errors errors = new BindException(cmd, "cmdBookdData");
		validateAddBook.validate((BookDataFormData) cmd, errors);
		Object value = errors.getFieldValue("isbn");
		assertEquals(0, errors.getErrorCount());
		assertEquals(isbn13, value);
	}

	@Test
	public void shouldFailForInvalidISBN() {
		String isbn13 = "978-3492285100-22";
		cmd.setIsbn(isbn13);
		Errors errors = new BindException(cmd, "cmdBookdData");
		validateAddBook.validate((BookDataFormData) cmd, errors);
		assertEquals(1, errors.getErrorCount());
	}

	@Test
	public void shouldFailForEmptyAutor() {
		Errors errors = new BindException(cmd, "cmdBookdData");
		cmd.setAuthor(null);
		validateAddBook.validate((BookDataFormData) cmd, errors);
		assertEquals(1, errors.getErrorCount());
	}

	@Test
	public void shouldFailForWhitespaceAutor() {
		Errors errors = new BindException(cmd, "cmdBookdData");
		cmd.setAuthor("    ");
		validateAddBook.validate((BookDataFormData) cmd, errors);
		assertEquals(1, errors.getErrorCount());
	}

	@Test
	public void shouldFailForNullAutor() {
		Errors errors = new BindException(cmd, "cmdBookdData");
		cmd.setAuthor(null);
		validateAddBook.validate((BookDataFormData) cmd, errors);
		assertEquals(1, errors.getErrorCount());
	}

	@Test
	public void shouldValidateAutor() {
		String title = "Title";
		cmd.setAuthor(title);
		Errors errors = new BindException(cmd, "cmdBookdData");
		validateAddBook.validate((BookDataFormData) cmd, errors);
		Object value = errors.getFieldValue("author");
		assertEquals(0, errors.getErrorCount());
		assertEquals(title, value);
	}

	@Test
	public void shouldFailForEmptyEdition() {
		Errors errors = new BindException(cmd, "cmdBookdData");
		cmd.setEdition(null);
		validateAddBook.validate((BookDataFormData) cmd, errors);
		assertEquals(1, errors.getErrorCount());
	}

	@Test
	public void shouldFailForWhitespaceEdition() {
		Errors errors = new BindException(cmd, "cmdBookdData");
		cmd.setEdition("    ");
		validateAddBook.validate((BookDataFormData) cmd, errors);
		assertEquals(1, errors.getErrorCount());
	}

	@Test
	public void shouldFailForNullEdition() {
		Errors errors = new BindException(cmd, "cmdBookdData");
		cmd.setEdition(null);
		validateAddBook.validate((BookDataFormData) cmd, errors);
		assertEquals(1, errors.getErrorCount());
	}

	@Test
	public void shouldValidateEdition() {
		String edition = "2";
		cmd.setEdition(edition);
		Errors errors = new BindException(cmd, "cmdBookdData");
		validateAddBook.validate((BookDataFormData) cmd, errors);
		Object value = errors.getFieldValue("edition");
		assertEquals(0, errors.getErrorCount());
		assertEquals(edition, value);
	}

	@Test
	public void shouldFailOnCharacterInEdition() {
		String edition = "2a";
		cmd.setEdition(edition);
		Errors errors = new BindException(cmd, "cmdBookdData");
		validateAddBook.validate((BookDataFormData) cmd, errors);
		assertEquals(1, errors.getErrorCount());
	}

	@Test(expected = RuntimeException.class)
	public void shouldFaildWithWrongFormDataObject() {
		Errors errors = mock(BindingResult.class);
		ReturnAllBooksFormData returnAllBooksFormData = new ReturnAllBooksFormData();
		validateAddBook.validate(returnAllBooksFormData, errors);
	}
}
