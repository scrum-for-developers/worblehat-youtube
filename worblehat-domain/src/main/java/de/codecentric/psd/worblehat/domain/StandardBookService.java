package de.codecentric.psd.worblehat.domain;

import org.apache.commons.collections.SetUtils;
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
    public void borrowBook(Book book, String borrowerEmail) throws BookAlreadyBorrowedException {
        // TODO: is it really necessary to retrieve the borrowing from the repository?
        Borrowing borrowing = borrowingRepository.findBorrowingForBook(book);
        if (borrowing != null) {
            throw new BookAlreadyBorrowedException("Book is already borrowed");
        } else {
            //book =findBookByIsbn(book.getIsbn());
            borrowing = new Borrowing(book, borrowerEmail, new DateTime());
            borrowingRepository.save(borrowing);
        }
    }

    @Override
    public void borrowOneBook(@Nonnull Set<Book> books, String borrower) throws BookAlreadyBorrowedException {
        Set<Book> unborrowedBooks = books.stream()
                .filter(book -> book.getBorrowing() == null)
                .collect(Collectors.toSet());
        if (!unborrowedBooks.isEmpty()) {
            boolean borrowed = false;
            for (Book book: unborrowedBooks) {
                try {
                    borrowBook(book, borrower);
                    borrowed = true;
                    break;
                } catch( BookAlreadyBorrowedException babe) {
                    continue;
                }
            }
            if (!borrowed)
                throw new BookAlreadyBorrowedException("All books are already borrowed");

        } else {
            return;
        }
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
    public Book createBook(String title, String author, String edition, String isbn, int yearOfPublication) {
        Book book = new Book(title, author, edition, isbn, yearOfPublication);

        // FIXME: there might be multiple copies of a books!
        Set<Book> booksByIsbn = bookRepository.findBooksByIsbn(isbn);

        if (booksByIsbn.isEmpty() || book.isSameCopy(booksByIsbn.iterator().next())) {
            return bookRepository.save(book);
        } else
            return null;
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
