package de.codecentric.psd.worblehat.web.validator;

import org.apache.commons.validator.EmailValidator;
import org.apache.commons.validator.ISBNValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import de.codecentric.psd.worblehat.web.command.BookBorrowFormData;

/**
 * Validation for adding a book
 * 
 * @author mahmut.can
 * 
 */
public class ValidateBorrowBook implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return BookBorrowFormData.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (!supports(target.getClass()))
			throw new IllegalArgumentException(
					"Not supported FormData for this validator: "
							+ target.getClass());

		BookBorrowFormData cmd = (BookBorrowFormData) target;

		checkThatIsbnIsFilledAndValid(errors, cmd);
		checkThatUserEmailAddressIsFilledAndValid(errors, cmd);
	}

	private void checkThatUserEmailAddressIsFilledAndValid(Errors errors,
			BookBorrowFormData cmd) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "empty");
		if (!errors.hasFieldErrors("email")) {
			if (!EmailValidator.getInstance().isValid(cmd.getEmail())) {
				errors.rejectValue("email", "notvalid");
			}
		}
	}

	private void checkThatIsbnIsFilledAndValid(Errors errors,
			BookBorrowFormData cmd) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isbn", "empty");
		if (!errors.hasFieldErrors("isbn")) {
			ISBNValidator isbnValidator = new ISBNValidator();
			if (!isbnValidator.isValid(cmd.getIsbn())) {
				errors.rejectValue("isbn", "notvalid");
			}
		}
	}

}
