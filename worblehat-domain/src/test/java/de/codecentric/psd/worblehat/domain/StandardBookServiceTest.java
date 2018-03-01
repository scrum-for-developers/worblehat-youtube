package de.codecentric.psd.worblehat.domain;

import java.util.*;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static org.hamcrest.CoreMatchers.*;
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

	private Book aBook, aCopyofBook, anotherBook;

	private Book aBorrowedBook, aCopyofBorrowedBook, anotherBorrowedBook;
    private Borrowing aBorrowing, aBorrowingOfCopy, anotherBorrowing;

	static {
	}

	@Before
	public void setup() {
		aBook = new Book("title", "author", "edition", "isbn", 2016);
		aCopyofBook = new Book("title", "author", "edition", "isbn", 2016);
		anotherBook = new Book("title2", "author2", "edition2", "isbn2", 2016);

		aBorrowedBook = new Book("title", "author", "edition", "isbn", 2016);
		aBorrowing = new Borrowing(aBorrowedBook, BORROWER_EMAIL, NOW);
		aBorrowedBook.borrowNowByBorrower(BORROWER_EMAIL);

		aCopyofBorrowedBook = new Book("title", "author", "edition", "isbn", 2016);
		aBorrowingOfCopy = new Borrowing(aCopyofBorrowedBook, BORROWER_EMAIL, NOW);
		aCopyofBorrowedBook.borrowNowByBorrower(BORROWER_EMAIL);

		anotherBorrowedBook = new Book("title2", "author2", "edition2", "isbn2", 2016);
		anotherBorrowing = new Borrowing(anotherBorrowedBook, BORROWER_EMAIL, NOW);
		anotherBorrowedBook.borrowNowByBorrower(BORROWER_EMAIL);

		bookRepository = mock(BookRepository.class);

        borrowingRepository = mock(BorrowingRepository.class);
        when(borrowingRepository.findBorrowingsByBorrower(BORROWER_EMAIL))
                .thenReturn(Arrays.asList(aBorrowing, anotherBorrowing));

        when(borrowingRepository.findBorrowingForBook(aBook)).thenReturn(null);

		bookService = new StandardBookService(borrowingRepository, bookRepository);

	}

	private void givenALibraryWith(Book... books) {
		Map <String,Set<Book>> bookCopies = new HashMap<>();
		for (Book book: books
			 ) {
			if (!bookCopies.containsKey(book.getIsbn())) {
				bookCopies.put(book.getIsbn(), new HashSet<>());
			}
			bookCopies.get(book.getIsbn()).add(book);
		}
		for (Map.Entry<String, Set<Book>> entry: bookCopies.entrySet()) {
			when(bookRepository.findBooksByIsbn(entry.getKey())).thenReturn(entry.getValue());
		}
	}

	@Test
	public void shouldReturnAllBooksOfOnePerson() {
		bookService.returnAllBooksByBorrower(BORROWER_EMAIL);
		verify(borrowingRepository).delete(anotherBorrowing);
	}

	@Test
	public void shouldSaveBorrowingWithBorrowerEmail() {
		givenALibraryWith(aBook);
		ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
		bookService.borrowBook(aBook.getIsbn(), BORROWER_EMAIL);
		verify(borrowingRepository).save(borrowingArgumentCaptor.capture());
		assertThat(borrowingArgumentCaptor.getValue().getBorrowerEmailAddress(), equalTo(BORROWER_EMAIL));
	}

	@Test()
	public void shouldNotBorrowWhenBookAlreadyBorrowed() {
		givenALibraryWith(aBorrowedBook);
        Optional<Borrowing> borrowing = bookService.borrowBook(aBorrowedBook.getIsbn(), BORROWER_EMAIL);
        assertTrue(!borrowing.isPresent());
    }

	@Test
	public void shouldSelectOneOfTwoBooksWhenBothAreNotBorrowed() {
		givenALibraryWith(aBook, aCopyofBook);
		ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
		bookService.borrowBook(aBook.getIsbn(), BORROWER_EMAIL);
		verify(borrowingRepository).save(borrowingArgumentCaptor.capture());
		assertThat(borrowingArgumentCaptor.getValue().getBorrowerEmailAddress(), is(BORROWER_EMAIL));
		assertThat(borrowingArgumentCaptor.getValue().getBorrowedBook(), either(is(aBook)).or(is(aCopyofBook)));
	}

	@Test
	public void shouldSelectUnborrowedOfTwoBooksWhenOneIsBorrowed() {
		givenALibraryWith(aBook, aBorrowedBook);
		ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
		bookService.borrowBook(aBook.getIsbn(), BORROWER_EMAIL);
		verify(borrowingRepository).save(borrowingArgumentCaptor.capture());
		assertThat(borrowingArgumentCaptor.getValue().getBorrowerEmailAddress(), is(BORROWER_EMAIL));
		assertThat(borrowingArgumentCaptor.getValue().getBorrowedBook(), is(aBook));
	}

	@Test
	public void shouldThrowExceptionWhenAllBooksAreBorrowedRightNow() {
		givenALibraryWith(aBorrowedBook, aCopyofBorrowedBook);
		ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
		Optional<Borrowing> borrowing = bookService.borrowBook(aBorrowedBook.getIsbn(), BORROWER_EMAIL);
		assertThat(borrowing, isEmpty());
		verify(borrowingRepository, never()).save(any(Borrowing.class));
	}

	@Test
	public void shouldCreateBook() {
		when(bookRepository.save(any(Book.class))).thenReturn(aBook);
		bookService.createBook(aBook.getTitle(), aBook.getAuthor(), aBook.getEdition(),
				aBook.getIsbn(), aBook.getYearOfPublication());

		// assert that book was saved to repository
		ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
		verify(bookRepository).save(bookArgumentCaptor.capture());

		// assert that the information was passed correctly to create the book
		assertThat(bookArgumentCaptor.getValue().getTitle(), is(aBook.getTitle()));
		assertThat(bookArgumentCaptor.getValue().getAuthor(), is(aBook.getAuthor()));
		assertThat(bookArgumentCaptor.getValue().getEdition(), is(aBook.getEdition()));
		assertThat(bookArgumentCaptor.getValue().getIsbn(), is(aBook.getIsbn()));
		assertThat(bookArgumentCaptor.getValue().getYearOfPublication(), is(aBook.getYearOfPublication()));
	}

	@Test
	public void shouldCreateAnotherCopyOfExistingBook() {
		when(bookRepository.save(any(Book.class))).thenReturn(aBook);
		bookService.createBook(aBook.getTitle(), aBook.getAuthor(), aBook.getEdition(),
				aBook.getIsbn(), aBook.getYearOfPublication());
		verify(bookRepository, times(1)).save(any(Book.class));
	}

	@Test
	public void shouldNotCreateAnotherCopyOfExistingBookWithDifferentTitle() {
		givenALibraryWith(aBook);
		bookService.createBook(aBook.getTitle()+"X", aBook.getAuthor(), aBook.getEdition(),
				aBook.getIsbn(), aBook.getYearOfPublication());
		verify(bookRepository, times(0)).save(any(Book.class));
	}

	@Test
	public void shouldNotCreateAnotherCopyOfExistingBookWithDifferentAuthor() {
		givenALibraryWith(aBook);
		bookService.createBook(aBook.getTitle(), aBook.getAuthor()+"X", aBook.getEdition(),
				aBook.getIsbn(), aBook.getYearOfPublication());
		verify(bookRepository, times(0)).save(any(Book.class));
	}

	@Test
	public void shouldFindAllBooks() {
		List<Book> expectedBooks = new ArrayList<>();
		expectedBooks.add(aBook);
		when(bookRepository.findAllBooks()).thenReturn(expectedBooks);
		List<Book> actualBooks = bookService.findAllBooks();
		assertThat(actualBooks, is(expectedBooks));
	}

	@Test
	public void shouldVerifyExistingBooks() {
		when(bookRepository.findBooksByIsbn(aBook.getIsbn())).thenReturn(Collections.singleton(aBook));
		Boolean bookExists = bookService.bookExists(aBook.getIsbn());
		assertTrue(bookExists);
	}

    @Test
    public void shouldVerifyNonexistingBooks() {
        when(bookRepository.findBooksByIsbn(aBook.getIsbn())).thenReturn(Collections.emptySet());
	    Boolean bookExists = bookService.bookExists(aBook.getIsbn());
	    assertThat(bookExists, is(false));
    }

    @Test
	public void shouldDeleteAllBooksAndBorrowings() {
		bookService.deleteAllBooks();
		verify(bookRepository).deleteAll();
		verify(borrowingRepository).deleteAll();
	}
}
