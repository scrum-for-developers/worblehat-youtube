package de.codecentric.psd.worblehat.service;

import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookRepository;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.domain.StandardBookService;
import org.junit.Before;
import org.junit.Test;

public class BookServiceTest {

	private BookRepository bookRepository;

	private BookService bookService;

	private Book testBook;

	@Before
	public void setup() throws Exception {
		bookRepository = mock(BookRepository.class);
		bookService = new StandardBookService(bookRepository);
		testBook = new Book("MyTitle", "MyAuthor", "2007", "ISBN-123132-21",
				2009);
		testBook.borrow("test@test.de");
	}

	@Test
	public void shouldReturnAllBooksOfOnePerson() {
		List<Book> result = Collections.singletonList(testBook);
		when(bookRepository.findAllBorrowBooksByBorrower(anyString()))
				.thenReturn(result);

		bookService.returnAllBooksByBorrower("test@test.de");

		assertNull(testBook.getCurrentBorrowing());
	}

}
