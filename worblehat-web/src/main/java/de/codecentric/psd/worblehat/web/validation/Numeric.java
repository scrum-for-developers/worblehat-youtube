package de.codecentric.psd.worblehat.web.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NumericConstraintValidator.class)
@Documented
public @interface Numeric {

    String message() default "{de.codecentric.psd.worblehat.web.validation.Numeric}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
