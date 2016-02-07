package de.codecentric.psd.worblehat.domain;

import com.mysema.query.types.expr.BooleanExpression;
import de.codecentric.psd.worblehat.domain.dto.BookListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public StandardBookService(BorrowingRepository borrowingRepository, BookRepository bookRepository) {
        this.borrowingRepository = borrowingRepository;
        this.bookRepository = bookRepository;
    }

    private BorrowingRepository borrowingRepository;

    private BookRepository bookRepository;

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
    public List<BookListDTO> findBooksWithBorrower() {
        List<Book> books = bookRepository.findAll();
        List<BookListDTO> bookListDTOs = new ArrayList<>();
        for (Book book : books){
            Borrowing borrowing = (Borrowing)borrowingRepository.findOne(QBorrowing.borrowing.borrowedBook.eq(book));
            BookListDTO bookListDTO;
            if (borrowing != null){
                bookListDTO = BookListDTO.createBookListDTO(book, borrowing.getBorrowerEmailAddress());
            }else{
                bookListDTO = BookListDTO.createBookListDTO(book, null);
            }
            bookListDTOs.add(bookListDTO);
        }
        return bookListDTOs;
    }

    @Override
    public void createBook(String title, String author, String edition, String isbn, int yearOfPublication) {
        Book book = new Book(title, author, edition, isbn, yearOfPublication);
        bookRepository.save(book);
    }

}
