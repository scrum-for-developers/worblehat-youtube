package de.codecentric.psd.worblehat.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The domain service class for book operations.
 * 
 * @author psd
 * 
 */
@Service
@Transactional
public class StandardBookService implements BookService {

	@Autowired
	private BookRepository bookRepository;

	public StandardBookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public StandardBookService() {
		super();
	}

	@Override
	public void returnAllBooksByBorrower(String string) {
		List<Book> borrowBooks = bookRepository
				.findAllBorrowBooksByBorrower(string);
		for (Book book : borrowBooks) {
			book.returnBook();

		}

	}
}
