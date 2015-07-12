package de.codecentric.psd.worblehat.web.command;

/**
 * Form data object from the borrow view.
 */
public class BookBorrowFormData {

	private String isbn;
	private String email;

	/**
	 * Empty constructor, required by Spring Framework.
	 */
	public BookBorrowFormData() {
		super();
	}

	/**
	 * Constructor for testing.
	 * 
	 * @param isbn
	 *            the isbn
	 * @param email
	 *            the user email address
	 */
	public BookBorrowFormData(String isbn, String email) {
		this.isbn = isbn;
		this.email = email;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
