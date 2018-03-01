package de.codecentric.psd.worblehat.domain;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The domain service class for book operations.
 */
@Service
@Transactional
public class StandardBookService implements BookService {

    public StandardBookService(){

    }

    @Autowired
    public StandardBookService(BorrowingRepository borrowingRepository, BookRepository bookRepository) {
        this.borrowingRepository = borrowingRepository;
        this.bookRepository = bookRepository;
    }

    private BorrowingRepository borrowingRepository;

    private BookRepository bookRepository;

    @Override
    public void returnAllBooksByBorrower(String borrowerEmailAddress) {
        List<Borrowing> borrowingsByUser = borrowingRepository
                .findBorrowingsByBorrower(borrowerEmailAddress);
        for (Borrowing borrowing : borrowingsByUser) {
            borrowingRepository.delete(borrowing);
        }
    }

    @Override
    public Optional<Borrowing> borrowBook(String isbn, String borrower) {
        Set<Book> books = bookRepository.findBooksByIsbn(isbn);

        Optional<Book> unborrowedBook = books.stream()
                .filter(book -> book.getBorrowing() == null)
                .findFirst();

        return unborrowedBook.map(book -> {
            book.borrowNowByBorrower(borrower);
            borrowingRepository.save(book.getBorrowing());
            return book.getBorrowing();
        });
    }

    @Override
    public Set<Book> findBooksByIsbn(String isbn) {
        return bookRepository.findBooksByIsbn(isbn); //null if not found
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAllBooks();
    }


    @Override
    public Optional<Book> createBook(@Nonnull String title,
                                     @Nonnull String author,
                                     @Nonnull String edition,
                                     @Nonnull String isbn,
                                     int yearOfPublication) {
        Book book = new Book(title, author, edition, isbn, yearOfPublication);

        Set<Book> booksByIsbn = bookRepository.findBooksByIsbn(isbn);

        if (booksByIsbn.isEmpty() || book.isSameCopy(booksByIsbn.iterator().next())) {
            return Optional.of(bookRepository.save(book));
        } else
            return Optional.empty();
    }

    @Override
    public boolean bookExists(String isbn) {
        Set<Book> books = bookRepository.findBooksByIsbn(isbn);
        return !books.isEmpty();
    }

    @Override
    public void deleteAllBooks() {
        borrowingRepository.deleteAll();
        bookRepository.deleteAll();
    }


}
