package de.codecentric.psd.worblehat.web.validator;

import org.apache.commons.validator.EmailValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import de.codecentric.psd.worblehat.web.command.ReturnAllBooksFormData;

/**
 * Validates email address when borrowed books are returned.
 */
public class ValidateReturnAllBooks implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ReturnAllBooksFormData.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (!supports(target.getClass()))
			throw new IllegalArgumentException(
					"Not supported FormData for this validator: "
							+ target.getClass());
		ReturnAllBooksFormData cmd = (ReturnAllBooksFormData) target;
		checkThatUserEmailAddressIsFilledAndValid(errors, cmd);

	}

	private void checkThatUserEmailAddressIsFilledAndValid(Errors errors,
			ReturnAllBooksFormData cmd) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress",
				"empty");
		if (!errors.hasFieldErrors("emailAddress")) {
			if (!EmailValidator.getInstance().isValid(cmd.getEmailAddress())) {
				errors.rejectValue("emailAddress", "notvalid");
			}
		}
	}

}
