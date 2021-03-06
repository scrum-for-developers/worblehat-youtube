package de.codecentric.psd.worblehat.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The domain service class for book operations.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class StandardBookService implements BookService {

    @NonNull
    private final BorrowingRepository borrowingRepository;

    @NonNull
    private final BookRepository bookRepository;

    @Override
    public void returnAllBooksByBorrower(String borrowerEmailAddress) {
        borrowingRepository.deleteByBorrowerEmailAddress(borrowerEmailAddress);
    }

    @Override
    public Optional<Borrowing> borrowBook(String isbn, String borrower) {
        Set<Book> books = bookRepository.findByIsbn(isbn);

        Optional<Book> unborrowedBook =
            books.stream().filter(book -> book.getBorrowing() == null).findFirst();

        return unborrowedBook.map(
            book -> {
                book.borrowNowByBorrower(borrower);
                borrowingRepository.save(book.getBorrowing());
                return book.getBorrowing();
            });
    }

    @Override
    public Set<Book> findBooksByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn); // null if not found
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAllByOrderByTitle();
    }

    @Override
    public Optional<Book> createBook(BookParameter bookParameter) {
        Book book = new Book(bookParameter);

        Optional<Book> bookFromRepo = bookRepository.findTopByIsbn(bookParameter.getIsbn());

        if (!bookFromRepo.isPresent() || book.isSameCopy(bookFromRepo.get())) {
            return Optional.of(bookRepository.save(book));
        } else return Optional.empty();
    }

    @Override
    public boolean bookExists(String isbn) {
        Set<Book> books = bookRepository.findByIsbn(isbn);
        return !books.isEmpty();
    }

    @Override
    public void deleteAllBooks() {
        borrowingRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Override
    public List<Borrowing> findAllBorrowingsByEmailAddress(String emailAddress) {
        return borrowingRepository.findByBorrowerEmailAddressOrderByBorrowDateAsc(emailAddress);
    }

    @Override
    public void removeBook(String isbn) {
        Set<Book> books = bookRepository.findByIsbn(isbn);

        Optional<Book> bookToDelete = books.stream()
            .filter(book -> book.getBorrowing() == null)
            .findFirst();

        bookToDelete.ifPresent(bookRepository::delete);
    }

}
