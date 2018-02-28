package de.codecentric.psd.worblehat.domain;

import java.util.*;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class StandardBookServiceTest {

	private BorrowingRepository borrowingRepository;

	private BookRepository bookRepository;

	private BookService bookService;

	private static final String BORROWER_EMAIL = "someone@codecentric.de";

	private static final DateTime NOW = DateTime.now();

	private static final Book TEST_BOOK = new Book("title", "author", "edition", "isbn", 2016);
	private static final Book TEST_BOOK2 = new Book("title2", "author2", "edition2", "isbn2", 2016);

	@Before
	public void setup() {
		borrowingRepository = mock(BorrowingRepository.class);
		bookRepository = mock(BookRepository.class);
		bookService = new StandardBookService(borrowingRepository, bookRepository);
	}

	@Test
	public void shouldReturnAllBooksOfOnePerson() {
		Borrowing borrowing = new Borrowing(TEST_BOOK, BORROWER_EMAIL, NOW);
		List<Borrowing> result = Collections.singletonList(borrowing);
		when(borrowingRepository.findBorrowingsByBorrower(BORROWER_EMAIL))
		.thenReturn(result);
		bookService.returnAllBooksByBorrower(BORROWER_EMAIL);
		verify(borrowingRepository).delete(borrowing);
	}

	@Test
	public void shouldSaveBorrowingWithBorrowerEmail() throws Exception {
		when(borrowingRepository.findBorrowingForBook(TEST_BOOK)).thenReturn(null);
		ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
		bookService.borrowBook(TEST_BOOK, BORROWER_EMAIL);
		verify(borrowingRepository).save(borrowingArgumentCaptor.capture());
		assertThat(borrowingArgumentCaptor.getValue().getBorrowerEmailAddress(), is(BORROWER_EMAIL));
	}

	@Test(expected = BookAlreadyBorrowedException.class)
	public void shouldThrowExceptionWhenBookAlreadyBorrowed() throws Exception {
		when(borrowingRepository.findBorrowingForBook(TEST_BOOK)).thenReturn(new Borrowing(TEST_BOOK, BORROWER_EMAIL, NOW));
		bookService.borrowBook(TEST_BOOK, BORROWER_EMAIL);
	}

	@Test
	public void shouldSelectOneOfTwoBooksWhenBothAreNotBorrowed() throws Exception {
		when(borrowingRepository.findBorrowingForBook(any(Book.class))).thenReturn(null);
		ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
		Set<Book> books = new HashSet<>();
		books.add(TEST_BOOK);
		books.add(TEST_BOOK2);
		bookService.borrowOneBook(books, BORROWER_EMAIL);
		verify(borrowingRepository).save(borrowingArgumentCaptor.capture());
		assertThat(borrowingArgumentCaptor.getValue().getBorrowerEmailAddress(), is(BORROWER_EMAIL));
		assertThat(borrowingArgumentCaptor.getValue().getBorrowedBook(), either(is(TEST_BOOK)).or(is(TEST_BOOK2)));
	}

	@Test
	public void shouldSelectUnborrowedOfTwoBooksWhenOneIsBorrowed() throws Exception {
		when(borrowingRepository.findBorrowingForBook(TEST_BOOK)).thenReturn(new Borrowing(TEST_BOOK, BORROWER_EMAIL, NOW));
		ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
		Set<Book> books = new HashSet<>();
		books.add(TEST_BOOK);
		books.add(TEST_BOOK2);
		bookService.borrowOneBook(books, BORROWER_EMAIL);
		verify(borrowingRepository).save(borrowingArgumentCaptor.capture());
		assertThat(borrowingArgumentCaptor.getValue().getBorrowerEmailAddress(), is(BORROWER_EMAIL));
		assertThat(borrowingArgumentCaptor.getValue().getBorrowedBook(), is(TEST_BOOK2));
	}

	@Test
	public void shouldThrowExceptionWhenAllBooksAreBorrowed() {
		when(borrowingRepository.findBorrowingForBook(TEST_BOOK)).thenReturn(new Borrowing(TEST_BOOK, BORROWER_EMAIL, NOW));
		when(borrowingRepository.findBorrowingForBook(TEST_BOOK2)).thenReturn(new Borrowing(TEST_BOOK2, BORROWER_EMAIL, NOW));
		ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
		Set<Book> books = new HashSet<>();
		books.add(TEST_BOOK);
		books.add(TEST_BOOK2);
		try {
			bookService.borrowOneBook(books, BORROWER_EMAIL);
			fail("Should have thrown Exception");
		} catch (BookAlreadyBorrowedException babe) {
			// OK
			assertTrue(true);
		}
		verify(borrowingRepository, never()).save(any(Borrowing.class));
	}

	@Test
	public void shouldCreateBook() {
		ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
		when(bookRepository.findBooksByIsbn(TEST_BOOK.getIsbn())).thenReturn(Collections.singleton(TEST_BOOK));
		bookService.createBook(TEST_BOOK.getTitle(), TEST_BOOK.getAuthor(), TEST_BOOK.getEdition(),
				TEST_BOOK.getIsbn(), TEST_BOOK.getYearOfPublication());
		verify(bookRepository).save(bookArgumentCaptor.capture());
		assertThat(bookArgumentCaptor.getValue().getTitle(), is(TEST_BOOK.getTitle()));
		assertThat(bookArgumentCaptor.getValue().getAuthor(), is(TEST_BOOK.getAuthor()));
		assertThat(bookArgumentCaptor.getValue().getEdition(), is(TEST_BOOK.getEdition()));
		assertThat(bookArgumentCaptor.getValue().getIsbn(), is(TEST_BOOK.getIsbn()));
		assertThat(bookArgumentCaptor.getValue().getYearOfPublication(), is(TEST_BOOK.getYearOfPublication()));
	}

	@Test
	public void shouldCreateAnotherCopyOfExistingBook() {
		givenAnExistingBook(TEST_BOOK);
		bookService.createBook(TEST_BOOK.getTitle(), TEST_BOOK.getAuthor(), TEST_BOOK.getEdition(),
				TEST_BOOK.getIsbn(), TEST_BOOK.getYearOfPublication());
		verify(bookRepository, times(1)).save(any(Book.class));
	}

	@Test
	public void shouldNotCreateAnotherCopyOfExistingBookWithDifferentTitle() {
		givenAnExistingBook(TEST_BOOK);
		bookService.createBook(TEST_BOOK.getTitle()+"X", TEST_BOOK.getAuthor(), TEST_BOOK.getEdition(),
				TEST_BOOK.getIsbn(), TEST_BOOK.getYearOfPublication());
		verify(bookRepository, times(0)).save(any(Book.class));
	}

	@Test
	public void shouldNotCreateAnotherCopyOfExistingBookWithDifferentAuthor() {
		givenAnExistingBook(TEST_BOOK);
		bookService.createBook(TEST_BOOK.getTitle(), TEST_BOOK.getAuthor()+"X", TEST_BOOK.getEdition(),
				TEST_BOOK.getIsbn(), TEST_BOOK.getYearOfPublication());
		verify(bookRepository, times(0)).save(any(Book.class));
	}

	private void givenAnExistingBook(Book testBook) {
		when(bookRepository.findBookByIsbn(testBook.getIsbn())).thenReturn(Optional.of(TEST_BOOK));
        when(bookRepository.findBooksByIsbn(testBook.getIsbn())).thenReturn(Collections.singleton(TEST_BOOK));
	}

	@Test
	public void shouldFindAllBooks() {
		List<Book> expectedBooks = new ArrayList<>();
		expectedBooks.add(TEST_BOOK);
		when(bookRepository.findAllBooks()).thenReturn(expectedBooks);
		List<Book> actualBooks = bookService.findAllBooks();
		assertThat(actualBooks, is(expectedBooks));
	}

	@Test
	public void shouldFindexistingBooks() {
		when(bookRepository.findBookByIsbn(TEST_BOOK.getIsbn())).thenReturn(Optional.of(TEST_BOOK));
		Optional<Book> actualBook = bookRepository.findBookByIsbn(TEST_BOOK.getIsbn());
		assertThat(actualBook.get(), is(TEST_BOOK));
	}

	@Test
	public void shouldVerifyExistingBooks() {
		when(bookRepository.findBooksByIsbn(TEST_BOOK.getIsbn())).thenReturn(Collections.singleton(TEST_BOOK));
		Boolean bookExists = bookService.bookExists(TEST_BOOK.getIsbn());
		assertTrue(bookExists);
	}

    @Test
    public void shouldVerifyNonexistingBooks() {
        when(bookRepository.findBooksByIsbn(TEST_BOOK.getIsbn())).thenReturn(Collections.emptySet());
	    Boolean bookExists = bookService.bookExists(TEST_BOOK.getIsbn());
	    assertThat(bookExists, is(false));
    }

    @Test
	public void shouldDeleteAllBooksAndBorrowings() {
		bookService.deleteAllBooks();
		verify(bookRepository).deleteAll();
		verify(borrowingRepository).deleteAll();
	}
}
