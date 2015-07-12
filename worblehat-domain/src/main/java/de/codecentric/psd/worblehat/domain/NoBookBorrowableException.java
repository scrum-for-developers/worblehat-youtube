package de.codecentric.psd.worblehat.domain;

/**
 * Business exception that is thrown in case no book is available.
 */
public class NoBookBorrowableException extends Exception {

	private static final long serialVersionUID = 1L;
	private final String isbn;

	public NoBookBorrowableException(String isbn) {
		super();
		this.isbn = isbn;
	}

	public String getIsbn() {
		return isbn;
	}

}
