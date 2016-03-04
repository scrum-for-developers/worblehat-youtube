package de.codecentric.psd.worblehat.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Borrowing Entity
 */
@Entity
public class Borrowing implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; // NOSONAR

	private String borrowerEmailAddress;

	@Temporal(TemporalType.DATE)
	private Date borrowDate;

	@OneToOne()
	private Book borrowedBook;

	public String getBorrowerEmailAddress() {
		return borrowerEmailAddress;
	}

	public Date getBorrowDate() {
		return borrowDate;
	}

	/**
	 * @param book
	 * The borrowed book
	 * @param borrowerEmailAddress
	 * The borrowers e-mail Address
	 * @param borrowDate
	 * The borrow date
     */
	public Borrowing(Book book, String borrowerEmailAddress, Date borrowDate) {
		super();
		this.borrowedBook = book;
		this.borrowerEmailAddress = borrowerEmailAddress;
		this.borrowDate = borrowDate;
	}

	private Borrowing() {
		// for JPA
	}

}
