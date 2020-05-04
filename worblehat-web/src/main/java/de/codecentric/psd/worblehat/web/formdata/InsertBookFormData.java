package de.codecentric.psd.worblehat.web.formdata;

import de.codecentric.psd.worblehat.domain.BookParameter;
import de.codecentric.psd.worblehat.web.validation.ISBN;
import de.codecentric.psd.worblehat.web.validation.Numeric;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/** This class represent the form data of the add book form. */
@Data
public class InsertBookFormData {

  @NotEmpty(message = "{empty.title}")
  private String title;

  @NotEmpty(message = "{empty.edition}")
  @Numeric(message = "{invalid.edition}")
  private String edition;

  @NotEmpty(message = "{empty.yearOfPublication}")
  @Numeric(message = "{invalid.yearOfPublication}")
  @Length(message = "{invalid.length.yearOfPublication}", min = 4, max = 4)
  private String yearOfPublication;

  @NotEmpty(message = "{empty.isbn}")
  @ISBN(message = "{invalid.isbn}")
  private String isbn;

  @NotEmpty(message = "{empty.author}")
  private String author;

  private String description;

  public BookParameter toBookParameter() {
    return new BookParameter(
        title, author, edition, isbn, new Integer(yearOfPublication), description);
  }
}
