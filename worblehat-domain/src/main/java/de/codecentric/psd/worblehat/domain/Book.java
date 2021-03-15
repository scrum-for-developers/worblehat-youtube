package de.codecentric.psd.worblehat.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Book implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NonNull private String title;

  @NonNull private String author;

  @NonNull private String edition;

  // TODO: convert String to an ISBN class, that ensures a valid ISBN
  @NonNull private String isbn;

  @NonNull private Integer yearOfPublication;

  @OneToOne(mappedBy = "borrowedBook", orphanRemoval = true)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Borrowing borrowing;

  @Column(columnDefinition = "TEXT")
  private String description;

  public Book(BookParameter bookParameter) {
    this(
        bookParameter.getTitle(),
        bookParameter.getAuthor(),
        bookParameter.getEdition(),
        bookParameter.getIsbn(),
        bookParameter.getYearOfPublication());
    this.description = bookParameter.getDescription();
  }

  Book(final Book book) {
    this(book.title, book.author, book.edition, book.isbn, book.yearOfPublication);
    this.description = book.description;
  }

  boolean isSameCopy(@NonNull Book book) {
    return getTitle().equals(book.title)
        && getAuthor().equals(book.author)
        && getEdition().equals(book.edition);
  }

  public void borrowNowByBorrower(String borrowerEmailAddress) {
    if (borrowing == null) {
      this.borrowing = new Borrowing(this, borrowerEmailAddress, LocalDate.now());
    }
  }
}
