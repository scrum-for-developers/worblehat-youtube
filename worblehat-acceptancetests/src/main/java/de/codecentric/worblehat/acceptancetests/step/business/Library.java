package de.codecentric.worblehat.acceptancetests.step.business;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.codecentric.psd.worblehat.domain.*;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Component
public class Library {

	private BookService bookService;


	@Autowired
	public Library(ApplicationContext applicationContext){
		this.bookService= applicationContext.getBean(BookService.class);
	}

	// *******************
	// *** G I V E N *****
	// *******************
	
	@Given("an empty library")
	public void emptyLibrary() throws SQLException{
		bookService.deleteAllBooks();
	}

	@Given("a library, containing a book with isbn <isbn>")
	public void createLibraryWithSingleBookWithGivenIsbn(@Named("isbn")String isbn){
		Book book = DemoBookFactory.createDemoBook().withISBN(isbn).build();
		bookService.createBook(book.getTitle(), book.getAuthor(), book.getEdition(),
				isbn, book.getYearOfPublication());
	}

	@Given("borrower <borrower1> has borrowed books <isbns1>")
	public void borrower1HasBorrowerdBooks(@Named("borrower1")String borrower,
										  @Named("isbns1")String isbns) throws BookAlreadyBorrowedException {
		borrowerHasBorrowedBooks(borrower, isbns);
	}

	@Given("borrower <borrower2> has borrowed books <isbns2>")
	public void borrower2HasBorrowerdBooks(@Named("borrower2")String borrower,
										   @Named("isbns2")String isbns) throws BookAlreadyBorrowedException {
		borrowerHasBorrowedBooks(borrower, isbns);
	}

	public void borrowerHasBorrowedBooks(String borrower, String isbns) throws BookAlreadyBorrowedException {
		List<String> isbnList = getListOfItems(isbns);
		for (String isbn: isbnList){
			Book book = DemoBookFactory.createDemoBook().withISBN(isbn).build();
			book = bookService.createBook(book.getTitle(), book.getAuthor(), book.getEdition(), isbn,
					book.getYearOfPublication());
			bookService.borrowBook(book, borrower);
		}
	}


	private List<String> getListOfItems(String isbns) {
		return isbns.isEmpty() ? Collections.<String>emptyList() : Arrays.asList(isbns.split(" "));
	}
	// *****************
	// *** W H E N *****
	// *****************
	
	// *****************
	// *** T H E N *****
	// *****************
	
	
	@Then("the library contains only the book with <isbn>")
	public void shouldContainOnlyOneBook(@Named("isbn") String isbn) throws SQLException {
		waitForServerResponse();
		List<Book> books = bookService.findAllBooks();
		assertThat(books.size(), is(1));
		assertThat(books.get(0).getIsbn(), is(isbn));
	}

	private void waitForServerResponse() {
		// normally you would have much better mechanisms for waiting for a
		// server response. We are choosing a simple solution for the sake of this
		// training 
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// pass
		}
	}


	

}
