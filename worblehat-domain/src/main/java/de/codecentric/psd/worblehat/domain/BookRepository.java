package de.codecentric.psd.worblehat.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	/**
	 * @param isbn the ISBN to search for
	 * @return any book with the given ISBN that is available for borrowing
	 */
	@Query("select book from Book book where book.isbn=:isbn and book.currentBorrowing is null")
	Book findBorrowableBook(@Param("isbn") String isbn);

	/**
	 * @param email the name of the borrowing user
	 * @return all books the given user is currently borrowing
	 */
	@Query("select book from Book as book where book.currentBorrowing.borrowerEmailAddress = :email")
	List<Book> findAllBorrowBooksByBorrower(@Param("email") String email);

	/**
	 * @return all books
	 */
	@Query("select book from Book book order by book.title")
	List<Book> findAllBooks();

}
