package de.codecentric.psd.worblehat.web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import de.codecentric.psd.worblehat.domain.BookFactory;
import de.codecentric.psd.worblehat.domain.BookRepository;
import de.codecentric.psd.worblehat.web.command.BookDataFormData;

public class InsertBookControllerTest {

	@InjectMocks
	private InsertBookController insertBookController;

	@Mock
	private HttpServletRequest mockRequest;

	@Mock
	private ModelMap mockModelMap;

	@Mock
	private BindingResult mockBindingResult;

	@Mock
	private BookFactory bookFactory;

	@Mock
	private BookRepository bookRepository;

	@Before
	public void setup() {
		insertBookController = new InsertBookController();
		MockitoAnnotations.initMocks(this);
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
		when(mockBindingResult.hasErrors()).thenReturn(false);
		String path = insertBookController.processSubmit(mockRequest,
				mockModelMap, cmd, mockBindingResult);

		verify(mockModelMap).put("bookDataFormData", cmd);
		verify(bookFactory).createBook("Test with JUnit", "Horst Tester", "2",
				"ISBN-123132-21", 1999);
		assertThat(path, is("/bookList"));
	}
}
