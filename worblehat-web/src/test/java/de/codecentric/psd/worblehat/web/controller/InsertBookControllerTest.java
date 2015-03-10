package de.codecentric.psd.worblehat.web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.codecentric.psd.worblehat.domain.BookFactory;
import de.codecentric.psd.worblehat.domain.BookRepository;
import de.codecentric.psd.worblehat.web.command.BookDataFormData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

public class InsertBookControllerTest {

	private InsertBookController insertBookController;

	private BookFactory bookFactory;

	private BookRepository bookRepository;

	@Before
	public void setup() {
		bookFactory = mock(BookFactory.class);
		bookRepository = mock(BookRepository.class);
		insertBookController = new InsertBookController(bookFactory, bookRepository);
	}

	@Test
	public void shouldInsertBook() {
		ModelMap modelMap = new ModelMap();
		insertBookController.setupForm(modelMap);
		Object object = modelMap.get("bookDataFormData");
		assertThat(object, not(nullValue()));
	}

	@Test
	public void shouldAddBook() {
		BookDataFormData cmd = new BookDataFormData();
		cmd.setIsbn("ISBN-123132-21");
		cmd.setAuthor("Horst Tester");
		cmd.setEdition("2");
		cmd.setTitle("Test with JUnit");
		cmd.setYear("1999");
		BindingResult mockBindingResult = mock(BindingResult.class);
		when(mockBindingResult.hasErrors()).thenReturn(false);
		ModelMap modelMap = new ModelMap();
		String path = insertBookController.processSubmit(
				modelMap, cmd, mockBindingResult);

		verify(bookFactory).createBook("Test with JUnit", "Horst Tester", "2",
				"ISBN-123132-21", 1999);
		assertThat(path, is("/bookList"));
		assertEquals(cmd, modelMap.get("bookDataFormData"));
	}
}
