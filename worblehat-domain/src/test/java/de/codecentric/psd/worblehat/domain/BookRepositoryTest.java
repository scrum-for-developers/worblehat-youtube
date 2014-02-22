package de.codecentric.psd.worblehat.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class BookRepositoryTest {

	@Mock
	private EntityManager emMock;

	@Mock
	private Query queryMock;

	@InjectMocks
	private BookRepository bookRepository;

	private Book validAvailableBook;

	@Before
	public void setup() {
		validAvailableBook = new Book("MyTitle", "MyAuthor", "2007",
				"ISBN-123132-21", 2009);
		bookRepository = new BookRepository();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldStoreBook() {

		bookRepository.store(validAvailableBook);
		verify(emMock).persist(validAvailableBook);
	}

	@Test
	public void shouldFindAllBorrowBooksByEmail() {
		List<Book> result = new ArrayList<Book>();
		result.add(validAvailableBook);

		when(emMock.createNamedQuery("findAllBorrowedBooksByEmail"))
				.thenReturn(queryMock);
		when(
				queryMock.setParameter(Mockito.isA(String.class),
						Mockito.isA(String.class))).thenReturn(queryMock);
		when(queryMock.getResultList()).thenReturn(result);

		List<Book> books = bookRepository
				.findAllBorrowBooksByBorrower("test@test.de");
		assertEquals(1, books.size());
	}

	@Test
	public void shouldFindBorrowableBook() throws NoBookBorrowableException {
		when(emMock.createNamedQuery("findBorrowableBookByISBN")).thenReturn(
				queryMock);
		when(
				queryMock.setParameter(Mockito.isA(String.class),
						Mockito.isA(String.class))).thenReturn(queryMock);
		when(queryMock.setMaxResults(Mockito.isA(Integer.class))).thenReturn(
				queryMock);
		when(queryMock.getSingleResult()).thenReturn(validAvailableBook);

		Book book = bookRepository.findBorrowableBook("1234");

		verify(queryMock).setParameter("isbn", "1234");
		assertNotNull(book);
	}

	@Test
	public void shouldNotFindAnyBorrowableBook() {
		when(emMock.createNamedQuery("findBorrowableBookByISBN")).thenReturn(
				queryMock);
		when(
				queryMock.setParameter(Mockito.isA(String.class),
						Mockito.isA(String.class))).thenReturn(queryMock);
		when(queryMock.setMaxResults(Mockito.isA(Integer.class))).thenReturn(
				queryMock);
		when(queryMock.getSingleResult()).thenThrow(new NoResultException());

		try {
			bookRepository.findBorrowableBook("1234");
			fail("exception expected");
		} catch (NoBookBorrowableException e) {
			assertThat(e.getIsbn(), is("1234"));
		}
	}

	@Test
	public void shouldSelectAllBooks() {
		List<Book> result = new ArrayList<Book>();
		result.add(validAvailableBook);

		when(emMock.createNamedQuery("findAllBooks")).thenReturn(queryMock);
		when(
				queryMock.setParameter(Mockito.isA(String.class),
						Mockito.isA(String.class))).thenReturn(queryMock);
		when(queryMock.getResultList()).thenReturn(result);

		List<Book> books = bookRepository.findAllBooks();

		verify(emMock).createNamedQuery("findAllBooks");

		assertEquals(1, books.size());
	}
}
