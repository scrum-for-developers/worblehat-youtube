package de.codecentric.psd.worblehat.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b from Book b order by title")
    public List<Book> findAllBooks();

    @Query("SELECT b from Book b WHERE b.isbn = :isbn")
    public Book findBookByIsbn(@Param("isbn")String isbn);

    @Query("SELECT b from Book b where b.isbn = :isbn")
    List<Book> findBooksByIsbn(String value);

    @Query("SELECT b from Book b where b.author = :author")
    List<Book> findBooksByAuthor(String author);

    @Query("SELECT b from Book b where b.edition = :edition")
    List<Book> findBooksByEdition(String edition);
}
