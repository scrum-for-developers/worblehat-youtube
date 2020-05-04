package de.codecentric.psd.worblehat.web.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NumericConstraintValidatorTest {

  private NumericConstraintValidator numericConstraintValidator;

  ConstraintValidatorContext constraintValidatorContext;

  @BeforeEach
  void setUp() throws Exception {
    numericConstraintValidator = new NumericConstraintValidator();
    constraintValidatorContext = mock(ConstraintValidatorContext.class);
  }

  @Test
  void initializeShouldTakeNumeric() throws Exception {
    Numeric numeric = mock(Numeric.class);
    numericConstraintValidator.initialize(numeric);
  }

  @Test
  void shouldReturnTrueIfBlank() throws Exception {
    boolean actual = numericConstraintValidator.isValid("", constraintValidatorContext);
    assertTrue(actual);
  }

  @Test
  void shouldReturnTrueIfNumeric() throws Exception {
    boolean actual = numericConstraintValidator.isValid("1", constraintValidatorContext);
    assertTrue(actual);
  }

  @Test
  void shouldReturnFalsIfNotNumeric() throws Exception {
    boolean actual = numericConstraintValidator.isValid("x", constraintValidatorContext);
    assertFalse(actual);
  }
}
