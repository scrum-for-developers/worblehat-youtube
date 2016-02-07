package de.codecentric.psd.worblehat.service;

import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import de.codecentric.psd.worblehat.domain.*;
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
		when(bookRepository.findAll(QBook.book.currentBorrowing.borrowerEmailAddress.eq("test@test.de")))
				.thenReturn(result);

		bookService.returnAllBooksByBorrower("test@test.de");

		assertNull(testBook.getCurrentBorrowing());
	}

}
