package de.codecentric.psd.worblehat.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The domain service class for book operations.
 */
@Service
@Transactional
public class StandardBookService implements BookService {

	private BookRepository bookRepository;

	@Autowired
	public StandardBookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
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
