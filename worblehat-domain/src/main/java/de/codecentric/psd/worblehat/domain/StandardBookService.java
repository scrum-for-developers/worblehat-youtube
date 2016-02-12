package de.codecentric.psd.worblehat.domain;

import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import de.codecentric.psd.worblehat.domain.dto.BookWithBorrowerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The domain service class for book operations.
 */
@Service
@Transactional
public class StandardBookService implements BookService {

    @Autowired
    public StandardBookService(BorrowingRepository borrowingRepository, BookRepository bookRepository,
                               EntityManager entityManager) {
        this.borrowingRepository = borrowingRepository;
        this.bookRepository = bookRepository;
        this.entityManager = entityManager;
    }

    private BorrowingRepository borrowingRepository;

    private BookRepository bookRepository;

    private EntityManager entityManager;

    @Override
    public void returnAllBooksByBorrower(String string) {
        List<Borrowing> borrowingsByUser = (List<Borrowing>) borrowingRepository
                .findAll(QBorrowing.borrowing.borrowerEmailAddress.eq(string));
        for (Borrowing borrowing : borrowingsByUser) {
            borrowingRepository.delete(borrowing);
        }
    }

    @Override
    public void borrowBook(Book book, String borrowerEmail) throws BookAlreadyBorrowedException {
        BooleanExpression isBookBorrowed = QBorrowing.borrowing.borrowedBook.eq(book);
        Borrowing borrowing = (Borrowing) borrowingRepository.findOne(isBookBorrowed);
        if (borrowing != null) {
            throw new BookAlreadyBorrowedException("Book is already borrowed");
        } else {
            borrowing = new Borrowing(book, borrowerEmail, new Date());
            borrowingRepository.save(borrowing);
        }
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        Book book = (Book) bookRepository.findOne(QBook.book.isbn.eq(isbn));
        return book; //null if not found
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<BookWithBorrowerDTO> findBooksWithBorrower() {
        QBook book = QBook.book;
        QBorrowing borrowing = QBorrowing.borrowing;

        List<Tuple> result = new JPAQuery(entityManager).from(borrowing).rightJoin(borrowing.borrowedBook, book)
                .list(book.title, book.author, book.edition, book.isbn, book.yearOfPublication, borrowing.borrowerEmailAddress);

        List<BookWithBorrowerDTO> booksWithBorrowerDTOs = new ArrayList<>();
        for (Tuple tuple : result){
            BookWithBorrowerDTO bookWithBorrowerDTO = mapTupleToBookWithBorrowerDTO(book, borrowing, tuple);
            booksWithBorrowerDTOs.add(bookWithBorrowerDTO);
        }
        return booksWithBorrowerDTOs;
    }

    private BookWithBorrowerDTO mapTupleToBookWithBorrowerDTO(QBook book, QBorrowing borrowing, Tuple tuple) {
        BookWithBorrowerDTO bookWithBorrowerDTO = new BookWithBorrowerDTO();
        bookWithBorrowerDTO.setAuthor(tuple.get(book.author));
        bookWithBorrowerDTO.setTitle(tuple.get(book.title));
        bookWithBorrowerDTO.setEdition(tuple.get(book.edition));
        bookWithBorrowerDTO.setIsbn(tuple.get(book.isbn));
        bookWithBorrowerDTO.setYearOfPublication(tuple.get(book.yearOfPublication));
        bookWithBorrowerDTO.setBorrower(tuple.get(borrowing.borrowerEmailAddress));
        return bookWithBorrowerDTO;
    }

    @Override
    public void createBook(String title, String author, String edition, String isbn, int yearOfPublication) {
        Book book = new Book(title, author, edition, isbn, yearOfPublication);
        bookRepository.save(book);
    }

}
