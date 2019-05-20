package de.codecentric.psd.worblehat.web.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class ISBNConstraintValidatorTest {

    private ISBNConstraintValidator isbnConstraintValidator;

    private ConstraintValidatorContext constraintValidatorContext;


    @BeforeEach
    void setUp() throws Exception {
        isbnConstraintValidator = new ISBNConstraintValidator();
        constraintValidatorContext = mock(ConstraintValidatorContext.class);
    }

    @Test
    void initializeShouldTakeIsbn() throws Exception {
        ISBN isbn = mock(ISBN.class);
        isbnConstraintValidator.initialize(isbn);
    }

    @Test
    void shouldReturnTrueIfBlank() throws Exception {
        boolean actual = isbnConstraintValidator.isValid("", constraintValidatorContext);
        assertTrue(actual);
    }

    @Test
    void shouldReturnTrueIfValidISBN10() throws Exception {
        boolean actual = isbnConstraintValidator.isValid("0132350882", constraintValidatorContext);
        assertTrue(actual);
    }

    @Test
    void shouldReturnFalseIfInvalidISBN10() throws Exception {
        boolean actual = isbnConstraintValidator.isValid("0123459789", constraintValidatorContext);
        assertFalse(actual);
    }

    @Test
    void shouldReturnTrueForValidISBN13() throws Exception {
        boolean actual = isbnConstraintValidator.isValid("9783827317247", constraintValidatorContext);
        assertTrue(actual);
    }

    @Test
    void shouldReturnFalseForInvalidISBN13() {
        boolean actual = isbnConstraintValidator.isValid("1234567890XXX", constraintValidatorContext);
        assertFalse(actual);
    }

}
