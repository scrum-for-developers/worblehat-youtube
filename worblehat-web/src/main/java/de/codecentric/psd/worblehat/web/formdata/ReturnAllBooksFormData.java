package de.codecentric.psd.worblehat.web.formdata;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * This class represent the form data of the return book form.
 */
@Data
public class ReturnAllBooksFormData {

    @NotEmpty(message = "{empty.email}")
    @Email(message = "{invalid.email}")
    private String emailAddress;
}
