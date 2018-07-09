package de.codecentric.psd.worblehat.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Borrowing Entity
 */
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Borrowing implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; // NOSONAR

	@NonNull
	@OneToOne()
	private Book borrowedBook;

	@NonNull
	private String borrowerEmailAddress;

	@Temporal(TemporalType.DATE)
	@NonNull
	private Date borrowDate;

	public String getBorrowerEmailAddress() {
		return borrowerEmailAddress;
	}

}
