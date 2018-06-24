package de.codecentric.psd.worblehat.web.formdata;

import de.codecentric.psd.worblehat.web.validation.ISBN;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Form data object from the borrow view.
 */
public class BorrowBookFormData {

	@NotEmpty(message = "{empty.isbn}")
	@ISBN(message = "{invalid.isbn}")
	private String isbn;

	@NotEmpty(message = "{empty.email}")
	@Email(message = "{invalid.email}")
	private String email;

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
