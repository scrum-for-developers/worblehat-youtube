package de.codecentric.psd.worblehat.domain;

import java.util.*;

import org.joda.time.DateTime;
import org.junit.Before;
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

	private static final Book A_BOOK = new Book("title", "author", "edition", "isbn", 2016);
	private static final Book ANOTHER_BOOK = new Book("title2", "author2", "edition2", "isbn2", 2016);

	private static final Book A_BORROWED_BOOK = new Book("title", "author", "edition", "isbn", 2016);
	private static final Book ANOTHER_BORROWED_BOOK = new Book("title2", "author2", "edition2", "isbn2", 2016);
	Set<Book> books;

	{
		A_BORROWED_BOOK.borrowNowByBorrower(BORROWER_EMAIL);
		ANOTHER_BORROWED_BOOK.borrowNowByBorrower(BORROWER_EMAIL);
	}

	@Before
	public void setup() {
		books = new HashSet<>();
		borrowingRepository = mock(BorrowingRepository.class);
		bookRepository = mock(BookRepository.class);
		bookService = new StandardBookService(borrowingRepository, bookRepository);
	}

	@Test
	public void shouldReturnAllBooksOfOnePerson() {
		Borrowing borrowing = new Borrowing(A_BOOK, BORROWER_EMAIL, NOW);
		List<Borrowing> result = Collections.singletonList(borrowing);
		when(borrowingRepository.findBorrowingsByBorrower(BORROWER_EMAIL))
		.thenReturn(result);
		bookService.returnAllBooksByBorrower(BORROWER_EMAIL);
		verify(borrowingRepository).delete(borrowing);
	}

	@Test
	public void shouldSaveBorrowingWithBorrowerEmail() throws Exception {
		when(borrowingRepository.findBorrowingForBook(A_BOOK)).thenReturn(null);
		ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
		bookService.borrowBook(A_BOOK, BORROWER_EMAIL);
		verify(borrowingRepository).save(borrowingArgumentCaptor.capture());
		assertThat(borrowingArgumentCaptor.getValue().getBorrowerEmailAddress(), is(BORROWER_EMAIL));
	}

	@Test(expected = BookAlreadyBorrowedException.class)
	public void shouldThrowExceptionWhenBookAlreadyBorrowed() throws Exception {
		bookService.borrowBook(A_BORROWED_BOOK, BORROWER_EMAIL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenNoBooksAreGiven() throws BookAlreadyBorrowedException {
		bookService.borrowOneBook(Collections.EMPTY_SET, BORROWER_EMAIL);
	}

	@Test
	public void shouldSelectOneOfTwoBooksWhenBothAreNotBorrowed() throws Exception {
		ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
		Collections.addAll(books, A_BOOK, ANOTHER_BOOK);
		bookService.borrowOneBook(books, BORROWER_EMAIL);
		verify(borrowingRepository).save(borrowingArgumentCaptor.capture());
		assertThat(borrowingArgumentCaptor.getValue().getBorrowerEmailAddress(), is(BORROWER_EMAIL));
		assertThat(borrowingArgumentCaptor.getValue().getBorrowedBook(), either(is(A_BOOK)).or(is(ANOTHER_BOOK)));
	}

	@Test
	public void shouldSelectUnborrowedOfTwoBooksWhenOneIsBorrowed() throws Exception {
		ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
		Collections.addAll(books, A_BORROWED_BOOK, ANOTHER_BOOK);
		bookService.borrowOneBook(books, BORROWER_EMAIL);
		verify(borrowingRepository).save(borrowingArgumentCaptor.capture());
		assertThat(borrowingArgumentCaptor.getValue().getBorrowerEmailAddress(), is(BORROWER_EMAIL));
		assertThat(borrowingArgumentCaptor.getValue().getBorrowedBook(), is(ANOTHER_BOOK));
	}

	@Test
	public void shouldThrowExceptionWhenAllBooksAreBorrowedRightNow() {
		ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
		Collections.addAll(books, A_BORROWED_BOOK, ANOTHER_BORROWED_BOOK);
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
	public void shouldThrowExceptionWhenAllBooksAreBorrowed() {
		Set<Book> books = testbooksWithBorrowing();
		try {
			bookService.borrowOneBook(books, BORROWER_EMAIL);
			fail("Should have thrown Exception");
		} catch (BookAlreadyBorrowedException babe) {
			// OK
			assertTrue(true);
		}
		verify(borrowingRepository, never()).save(any(Borrowing.class));
	}

	private Set<Book> testbooksWithBorrowing() {
		Collections.addAll(books, A_BORROWED_BOOK, ANOTHER_BORROWED_BOOK);
		return books;
	}

	@Test
	public void shouldCreateBook() {
		ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
		when(bookRepository.findBooksByIsbn(A_BOOK.getIsbn())).thenReturn(Collections.singleton(A_BOOK));
		bookService.createBook(A_BOOK.getTitle(), A_BOOK.getAuthor(), A_BOOK.getEdition(),
				A_BOOK.getIsbn(), A_BOOK.getYearOfPublication());
		verify(bookRepository).save(bookArgumentCaptor.capture());
		assertThat(bookArgumentCaptor.getValue().getTitle(), is(A_BOOK.getTitle()));
		assertThat(bookArgumentCaptor.getValue().getAuthor(), is(A_BOOK.getAuthor()));
		assertThat(bookArgumentCaptor.getValue().getEdition(), is(A_BOOK.getEdition()));
		assertThat(bookArgumentCaptor.getValue().getIsbn(), is(A_BOOK.getIsbn()));
		assertThat(bookArgumentCaptor.getValue().getYearOfPublication(), is(A_BOOK.getYearOfPublication()));
	}

	@Test
	public void shouldCreateAnotherCopyOfExistingBook() {
		givenAnExistingBook(A_BOOK);
		bookService.createBook(A_BOOK.getTitle(), A_BOOK.getAuthor(), A_BOOK.getEdition(),
				A_BOOK.getIsbn(), A_BOOK.getYearOfPublication());
		verify(bookRepository, times(1)).save(any(Book.class));
	}

	@Test
	public void shouldNotCreateAnotherCopyOfExistingBookWithDifferentTitle() {
		givenAnExistingBook(A_BOOK);
		bookService.createBook(A_BOOK.getTitle()+"X", A_BOOK.getAuthor(), A_BOOK.getEdition(),
				A_BOOK.getIsbn(), A_BOOK.getYearOfPublication());
		verify(bookRepository, times(0)).save(any(Book.class));
	}

	@Test
	public void shouldNotCreateAnotherCopyOfExistingBookWithDifferentAuthor() {
		givenAnExistingBook(A_BOOK);
		bookService.createBook(A_BOOK.getTitle(), A_BOOK.getAuthor()+"X", A_BOOK.getEdition(),
				A_BOOK.getIsbn(), A_BOOK.getYearOfPublication());
		verify(bookRepository, times(0)).save(any(Book.class));
	}

	private void givenAnExistingBook(Book testBook) {
        when(bookRepository.findBooksByIsbn(testBook.getIsbn())).thenReturn(Collections.singleton(A_BOOK));
	}

	@Test
	public void shouldFindAllBooks() {
		List<Book> expectedBooks = new ArrayList<>();
		expectedBooks.add(A_BOOK);
		when(bookRepository.findAllBooks()).thenReturn(expectedBooks);
		List<Book> actualBooks = bookService.findAllBooks();
		assertThat(actualBooks, is(expectedBooks));
	}

	@Test
	public void shouldVerifyExistingBooks() {
		when(bookRepository.findBooksByIsbn(A_BOOK.getIsbn())).thenReturn(Collections.singleton(A_BOOK));
		Boolean bookExists = bookService.bookExists(A_BOOK.getIsbn());
		assertTrue(bookExists);
	}

    @Test
    public void shouldVerifyNonexistingBooks() {
        when(bookRepository.findBooksByIsbn(A_BOOK.getIsbn())).thenReturn(Collections.emptySet());
	    Boolean bookExists = bookService.bookExists(A_BOOK.getIsbn());
	    assertThat(bookExists, is(false));
    }

    @Test
	public void shouldDeleteAllBooksAndBorrowings() {
		bookService.deleteAllBooks();
		verify(bookRepository).deleteAll();
		verify(borrowingRepository).deleteAll();
	}
}
