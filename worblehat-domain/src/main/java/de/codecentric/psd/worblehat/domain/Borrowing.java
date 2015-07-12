package de.codecentric.psd.worblehat.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

	public String getBorrowerEmailAddress() {
		return borrowerEmailAddress;
	}

	public Date getBorrowDate() {
		return borrowDate;
	}

	Borrowing(String borrowerEmailAddress, Date borrowDate) {
		super();
		this.borrowerEmailAddress = borrowerEmailAddress;
		this.borrowDate = borrowDate;
	}

	private Borrowing() {
		// for JPA
	}

}
