package de.codecentric.psd.worblehat.web.command;

import de.codecentric.psd.worblehat.web.validation.ISBN;
import de.codecentric.psd.worblehat.web.validation.Numeric;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * This class represent the form data of the add book form.
 */
public class BookDataFormData {

	@NotEmpty(message = "{empty.bookDataFormData.title}")
	private String title;

	@NotEmpty(message = "{empty.bookDataFormData.edition}")
	@Numeric(message = "{notvalid.bookDataFormData.edition}")
	private String edition;

	@NotEmpty(message = "{empty.bookDataFormData.year}")
	@Numeric(message = "{notvalid.bookDataFormData.year}")
	@Length(message = "{invalid.length.bookDataFormData.year}", min = 4, max = 4)
	private String year;

	@NotEmpty(message = "{empty.bookDataFormData.isbn}")
	@ISBN(message = "{notvalid.bookDataFormData.isbn}")
	private String isbn;

	@NotEmpty(message = "{empty.bookDataFormData.author}")
	private String author;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
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

	@Override
	public String toString() {
		return "BookDataFormData [title=" + title + ", edition=" + edition
				+ ", year=" + year + ", isbn=" + isbn + ", author=" + author
				+ "]";
	}

}
