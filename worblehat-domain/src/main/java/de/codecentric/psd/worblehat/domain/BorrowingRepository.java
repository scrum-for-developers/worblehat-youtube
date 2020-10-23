package de.codecentric.psd.worblehat.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {

  Optional<Borrowing> findByBorrowedBook(Book book);

  @Modifying
  void deleteByBorrowerEmailAddress(String borrowerEmailAddress);

  //TODO new findBy
}
