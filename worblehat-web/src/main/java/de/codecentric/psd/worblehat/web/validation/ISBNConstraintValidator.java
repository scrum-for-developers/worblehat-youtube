package de.codecentric.psd.worblehat.web.validation;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.ISBNValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ISBNConstraintValidator implements ConstraintValidator<ISBN, String> {

    private static final ISBNValidator INSTANCE = ISBNValidator.getInstance();

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        // Don't validate null, empty and blank strings, since these are validated by @NotNull, @NotEmpty and @NotBlank
        if (StringUtils.isNotBlank(isbn)) {
            return INSTANCE.isValid(isbn);
        }
        return true;
    }

}
