package de.codecentric.psd.worblehat.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class BookTest {
	
	@Test
	public void shouldAllowBorrowingAvailableBook() throws BookAlreadyBorrowedException{
		Book book = new Book("Title", "author", "2", "1234", 2002);
		book.borrow("borrower@codecentric.local");
		
		assertNotNull(book.getCurrentBorrowing());
	}
	
	@Test(expected =BookAlreadyBorrowedException.class)
	public void shouldDenyBorrowingAlreadyBorrowedBook() throws BookAlreadyBorrowedException{
		Book book = new Book("Title", "author", "2", "1234", 2002);
		book.borrow("borrower@codecentric.local");
		book.borrow("borrower@codecentric.local");
	}

}
