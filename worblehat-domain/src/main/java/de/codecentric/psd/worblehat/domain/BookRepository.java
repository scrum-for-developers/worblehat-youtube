package de.codecentric.psd.worblehat.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repo for Book
 */
@Repository
@Transactional
public class BookRepository {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Constructor for test.
	 * 
	 * @param em
	 */
	public BookRepository(EntityManager em) {
		this.em = em;
	}

	/**
	 * 
	 */
	public BookRepository() {
		// empty
	}

	/**
	 * Select a book by ISBN.
	 * 
	 * @param isbn
	 *            a valid ISBN_10 of a book entity
	 * @return null if no book found
	 */
	@SuppressWarnings("unchecked")
	public List<Book> findBooksByISBN(String isbn) {
		Query query = em.createQuery("form Book where isbn =? ").setParameter(
				1, isbn);
		return query.getResultList();
	}

	public Book findBookByUserAndISBN(String email, String isbn) {
		Query query = em.createQuery("form Book where isbn =? and email= ?")
				.setParameter(1, isbn).setParameter(2, email);
		return (Book) query.getSingleResult();
	}

	/**
	 * Persist a book entity to DB.
	 * 
	 * @param book
	 *            the book to persist
	 */
	public void store(Book book) {
		em.persist(book);
	}

	/**
	 * @param isbn
	 *            the ISBN to search for
	 * @return any book with the given ISBN that is available for borrowing
	 * 
	 * @throws NoBookBorrowableException
	 *             if no book is available
	 */
	public Book findBorrowableBook(String isbn)
			throws NoBookBorrowableException {
		try {
			return (Book) em.createNamedQuery("findBorrowableBookByISBN")
					.setParameter("isbn", isbn).setMaxResults(1)
					.getSingleResult();
		} catch (NoResultException e) {
			throw new NoBookBorrowableException(isbn);
		}
	}

	/**
	 * @param email
	 *            the name of the borrowing user
	 * @return all books the given user is currently borrowing
	 */
	@SuppressWarnings("unchecked")
	public List<Book> findAllBorrowBooksByBorrower(String email) {
		Query query = em.createNamedQuery("findAllBorrowedBooksByEmail");

		return query.setParameter("email", email).getResultList();
	}

	/**
	 * @return all books
	 */
	@SuppressWarnings("unchecked")
	public List<Book> findAllBooks() {
		Query query = em.createNamedQuery("findAllBooks");
		return query.getResultList();
	}

}
