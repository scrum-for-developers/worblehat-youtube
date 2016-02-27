package de.codecentric.psd.atdd.step.business;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookAlreadyBorrowedException;
import de.codecentric.psd.worblehat.domain.BookService;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;

import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Library {
	
	private BookService bookService;

	@Autowired
	public Library(BookService bookService){
		this.bookService = bookService;
	}

	// *******************
	// *** G I V E N *****
	// *******************
	
	@Given("an empty library")
	public void emptyLibrary() throws SQLException{
		bookService.deleteAllBooks();
	}
	
	@Given("a library with only a single unborrowed book with <isbn>")
	public void createSingleBook(@Named("isbn") String isbn) throws SQLException{
		emptyLibrary();
		bookService.createBook("Title", "Author", "1", isbn, 2011);
	}
	
	@Given("a user <user> has borrowed books <isbns>")
	public void createListOfBorrowedBooks(@Named("user") String user, @Named("isbns") String isbns) throws SQLException, BookAlreadyBorrowedException {
		List<String> isbnList = getListOfItems(isbns);
		for (String isbn : isbnList) {
			Book book = bookService.createBook("Title", "Author", "1", isbn, 2011);
			bookService.borrowBook(book, user);
		}
	}

	@Given("a user <user2> has borrowed books <isbns2>")
	public void createListOfBorrowedBooks2(@Named("user2") String user, @Named("isbns2") String isbns) throws SQLException, BookAlreadyBorrowedException {
		createListOfBorrowedBooks(user, isbns);
	}
	
	private List<String> getListOfItems(String isbns) {
		return Arrays.asList(isbns.split(" "));
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

	@Then("the library contains a no book with an <attribute> of <value>")
	public void thenTheDatabaseContainsANoEntryForBookisbn(@Named("attribute") String attribute,
			@Named("value") String value) throws SQLException{
		waitForServerResponse();
		List<Book> books= getBooksForAttribute(attribute, value);
		assertThat(books.size(), is(0));
	}


	@Then("the book <isbn> is not available for borrowing anymore")
	public void shouldNotBeAvailableForBorrowing(@Named("isbn") String isbn) throws SQLException{
		waitForServerResponse();
		bookService.bo

		database.shouldReturnNothing("SELECT * FROM book WHERE isbn = '"+isbn+"' AND current_borrowing_id is null");
	}

	@Then("the user <user> has borrowed the book <isbn>")
	public void thenTheUseruserHasBorrowedTheBookisbn(@Named("user") String user, @Named("isbn") String isbn) throws SQLException{
		waitForServerResponse();
		String id = database.getResult("SELECT id FROM borrowing WHERE borrower_email_address='"+user+"'");
		database.shouldReturnExactlyOne("SELECT * FROM book WHERE current_borrowing_id="+id+" AND isbn='"+isbn+"'");
	}

	@Then("books <isbns> are not borrowed anymore by user <user>")
	public void shouldNotBorrowBooks(@Named("isbns") String isbns, @Named("user") String user) throws SQLException{
		waitForServerResponse();
		database.shouldReturnNothing("SELECT * FROM borrowing WHERE borrower_email_address='"+user+"'");
	}

	@Then("books <isbns2> are still borrowed by user <user2>")
	public void userShouldBorrowBooks(@Named("isbns") String isbns, @Named("user") String user) throws SQLException {
		waitForServerResponse();
		List<String> isbnList = getListOfItems(isbns);
		for (String isbn : isbnList) {
			database.shouldReturnExactlyOne("SELECT count(*) FROM book b LEFT JOIN borrowing g ON b.current_borrowing_id = g.id " +
					"WHERE borrower_email_address = '"+user+"' and isbn = '"+isbn+"'");
		}
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
	
	// *****************
	// *** U T I L ***** 
	// *****************

	private List<Book> getBooksForAttribute(String attribute, String value) {
		if (attribute.equals("ISBN"))
			return bookService.findBooksByIsbn(value);
		if (attribute.equals("Author"))
			return bookService.findBooksByAuthor(value);
		if (attribute.equals("Edition"))
			return bookService.findBooksByEdition(value);
		return null;
	}




	

}
