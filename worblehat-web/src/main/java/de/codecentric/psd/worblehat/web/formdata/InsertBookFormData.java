package de.codecentric.psd.worblehat.web.formdata;

import de.codecentric.psd.worblehat.domain.BookParameter;
import de.codecentric.psd.worblehat.web.validation.ISBN;
import de.codecentric.psd.worblehat.web.validation.Numeric;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * This class represent the form data of the add book form.
 */
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

    public String getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(String yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "InsertBookFormData{" +
                "title='" + title + '\'' +
                ", edition='" + edition + '\'' +
                ", yearOfPublication='" + yearOfPublication + '\'' +
                ", isbn='" + isbn + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

	public BookParameter toBookParameter() {
		return new BookParameter(title, author, edition, isbn, new Integer(yearOfPublication), description);
	}

}
