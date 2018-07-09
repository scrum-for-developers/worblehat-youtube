package de.codecentric.psd.worblehat.web.formdata;

import de.codecentric.psd.worblehat.web.validation.ISBN;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Form data object from the borrow view.
 */
@Data
public class BorrowBookFormData {

	@NotEmpty(message = "{empty.isbn}")
	@ISBN(message = "{invalid.isbn}")
	private String isbn;

	@NotEmpty(message = "{empty.email}")
	@Email(message = "{invalid.email}")
	private String email;

}
