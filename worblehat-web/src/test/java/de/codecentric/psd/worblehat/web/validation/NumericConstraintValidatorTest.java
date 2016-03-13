package de.codecentric.psd.worblehat.web.validation;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class NumericConstraintValidatorTest {

    private NumericConstraintValidator numericConstraintValidator;

    ConstraintValidatorContext constraintValidatorContext;

    @Before
    public void setUp() throws Exception {
        numericConstraintValidator = new NumericConstraintValidator();
        constraintValidatorContext = mock(ConstraintValidatorContext.class);
    }

    @Test
    public void initializeShouldTakeNumeric() throws Exception {
        Numeric numeric = mock(Numeric.class);
        numericConstraintValidator.initialize(numeric);
    }

    @Test
    public void shouldReturnTrueIfBlank() throws Exception {
        boolean actual = numericConstraintValidator.isValid("", constraintValidatorContext);
        assertTrue(actual);
    }

    @Test
    public void shouldReturnTrueIfNumeric() throws Exception {
        boolean actual = numericConstraintValidator.isValid("1", constraintValidatorContext);
        assertTrue(actual);
    }

    @Test
    public void shouldReturnFalsIfNotNumeric() throws Exception {
        boolean actual = numericConstraintValidator.isValid("x", constraintValidatorContext);
        assertFalse(actual);
    }
}