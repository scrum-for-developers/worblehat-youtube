package de.codecentric.psd.worblehat.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookRepository extends JpaRepository<Book, Long> {

    // TODO: get rid of Query annotations

    @Query("SELECT b from Book b order by title")
    List<Book> findAllBooks();

    @Query("SELECT b from Book b where b.isbn = :isbn")
    Set<Book> findBooksByIsbn(@Param("isbn") String isbn);

    @Query("SELECT b from Book b where b.author = :author")
    Set<Book> findBooksByAuthor(String author);

    @Query("SELECT b from Book b where b.edition = :edition")
    Set<Book> findBooksByEdition(String edition);
}
