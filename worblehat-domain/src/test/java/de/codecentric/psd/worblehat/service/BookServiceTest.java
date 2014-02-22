package de.codecentric.psd.worblehat.service;

import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookRepository;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.domain.StandardBookService;

public class BookServiceTest {

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	BookService bookService;

	Book testBook;

	@Before
	public void setup() throws Exception {

		bookService = new StandardBookService();
		MockitoAnnotations.initMocks(this);
		testBook = new Book("MyTitle", "MyAuthor", "2007", "ISBN-123132-21",
				2009);
		testBook.borrow("test@test.de");

	}

	@Test
	public void shouldReturnAllBooksOfOnePerson() {
		List<Book> result = new ArrayList<Book>();
		result.add(testBook);
		when(bookRepository.findAllBorrowBooksByBorrower(anyString()))
				.thenReturn(result);

		bookService.returnAllBooksByBorrower("test@test.de");

		assertNull(testBook.getCurrentBorrowing());

	}
}
