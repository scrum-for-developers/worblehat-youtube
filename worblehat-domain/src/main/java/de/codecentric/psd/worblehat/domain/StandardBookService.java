package de.codecentric.psd.worblehat.domain;

import java.util.List;

import com.mysema.query.types.expr.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The domain service class for book operations.
 */
@Service
@Transactional
public class StandardBookService implements BookService {

	@Autowired
	public StandardBookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	private BookRepository bookRepository;

	@Override
	public void returnAllBooksByBorrower(String string) {
		QBook qbook = QBook.book;
		BooleanExpression booksByBorrower = qbook.currentBorrowing.borrowerEmailAddress.eq(string);
		List<Book> borrowBooks = (List<Book>) bookRepository.findAll(booksByBorrower);
		for (Book book : borrowBooks) {
			book.returnBook();
		}
	}

}
