package de.codecentric.psd.worblehat.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 * Entity implementation class for Entity: Book
 */
@Entity
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String title;
	private String author;
	private String edition;
	private String isbn;
	private int yearOfPublication;

	/**
	 * Empty constructor needed by Hibernate.
	 */
	private Book() {
		super();
	}

	/**
	 * Creates a new book instance.
	 * 
	 * @param title
	 *            the title
	 * @param author
	 *            the author
	 * @param edition
	 *            the edition
	 * @param isbn
	 *            the isbn
	 * @param yearOfPublication
	 *            the yearOfPublication
	 */
	public Book(String title, String author, String edition, String isbn,
			int yearOfPublication) {
		super();
		this.title = title;
		this.author = author;
		this.edition = edition;
		this.isbn = isbn;
		this.yearOfPublication = yearOfPublication;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getEdition() {
		return edition;
	}

	public String getIsbn() {
		return isbn;
	}

	public int getYearOfPublication() {
		return yearOfPublication;
	}

}
