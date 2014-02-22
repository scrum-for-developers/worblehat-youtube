package de.codecentric.psd.atdd.step.business;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;

import com.google.inject.Inject;

import de.codecentric.psd.atdd.library.DatabaseAdapter;

public class Library {
	
	public DatabaseAdapter database;

	@Inject
	public Library(DatabaseAdapter database) {
		this.database = database;
	}
	
	// *******************
	// *** G I V E N *****
	// *******************
	
	@Given("an empty library")
	public void emptyLibrary() throws SQLException{ 
		database.emptyTable("Book");
		database.emptyTable("Borrowing");
	}
	
	@Given("a library with only a single unborrowed book with <isbn>")
	public void createSingleBook(@Named("isbn") String isbn) throws SQLException{
		emptyLibrary();
		database.execute("INSERT INTO Book(id,title,author,edition,isbn,year) VALUES " +
				"(0, 'Title', 'Author', '1', '"+isbn+"', 2011)");
	}
	
	@Given("a user <user> has borrowed books <isbns>")
	public void createListOfBorrowedBooks(@Named("user") String user, @Named("isbns") String isbns) throws SQLException{
		List<String> isbnList = getListOfItems(isbns);
		for (String isbn : isbnList) {
			database.execute("INSERT INTO Book(title,author,edition,isbn,year) VALUES " +
					"('Title', 'Author', '1', '"+isbn+"', 2011)");
			String bookId = database.getResult("SELECT  LAST_INSERT_ID()");
			database.execute("INSERT INTO Borrowing(borrowDate, borrowerEmailAddress) VALUES " +
					"(CURDATE(), '"+user+"')");
			String borrowingId = database.getResult("SELECT  LAST_INSERT_ID()");
			database.execute("UPDATE Book SET currentBorrowing_id = "+borrowingId+" WHERE id = "+bookId);
			
		}
	}

	@Given("a user <user2> has borrowed books <isbns2>")
	public void createListOfBorrowedBooks2(@Named("user2") String user, @Named("isbns2") String isbns) throws SQLException{
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
	public void shouldContainOnlyOneBook(@Named("isbn") String isbn) throws SQLException
	{  
		database.shouldReturnExactlyOne("SELECT * FROM Book WHERE isbn = '" + isbn + "'");
	}

	@Then("the library contains a no book with an <attribute> of <value>")
	public void thenTheDatabaseContainsANoEntryForBookisbn(@Named("attribute") String attribute,
			@Named("value") String value) throws SQLException{
		database.shouldReturnNothing("SELECT * FROM Book WHERE " + getColumnForAttribute(attribute) + " = '" + value + "'");
	}

	@Then("the book <isbn> is not available for borrowing anymore")
	public void shouldNotBeAvailableForBorrowing(@Named("isbn") String isbn) throws SQLException{
		database.shouldReturnNothing("SELECT * FROM Book WHERE isbn = '"+isbn+"' AND currentBorrowing_id is null");
	}

	@Then("the user <user> has borrowed the book <isbn>")
	public void thenTheUseruserHasBorrowedTheBookisbn(@Named("user") String user, @Named("isbn") String isbn) throws SQLException{
		String id = database.getResult("SELECT id FROM Borrowing WHERE borrowerEmailAddress='"+user+"'");
		database.shouldReturnExactlyOne("SELECT * FROM Book WHERE currentBorrowing_id="+id+" AND isbn='"+isbn+"'");
	}

	@Then("books <isbns> are not borrowed anymore by user <user>")
	public void shouldNotBorrowBooks(@Named("isbns") String isbns, @Named("user") String user) throws SQLException{
		database.shouldReturnNothing("SELECT * FROM Borrowing WHERE borrowerEmailAddress='"+user+"'");
	}

	@Then("books <isbns2> are still borrowed by user <user2>")
	public void userShouldBorrowBooks(@Named("isbns") String isbns, @Named("user") String user) throws SQLException{
		List<String> isbnList = getListOfItems(isbns);
		for (String isbn : isbnList) {
			database.shouldReturnExactlyOne("SELECT count(*) FROM Book b LEFT JOIN Borrowing g ON b.currentBorrowing_id = g.id " +
					"WHERE borrowerEmailAddress = '"+user+"' and isbn = '"+isbn+"'");
		}
	}
	
	// *****************
	// *** U T I L ***** 
	// *****************

	private String getColumnForAttribute(String attribute) {
		if (attribute.equals("ISBN"))
			return "isbn";
		if (attribute.equals("Author"))
			return "author";
		if (attribute.equals("Edition"))
			return "edition";
		return null;
	}




	

}
