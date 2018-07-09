package de.codecentric.psd.worblehat.web.formdata;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * This class represent the form data of the return book form.
 */
@Data
public class ReturnAllBooksFormData {

	@NotEmpty(message = "{empty.email}")
	@Email(message = "{invalid.email}")
	private String emailAddress;
}
