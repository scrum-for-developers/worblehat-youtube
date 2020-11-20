package de.codecentric.psd.worblehat.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {

    Optional<Borrowing> findByBorrowedBook(Book book);

    @Modifying
    void deleteByBorrowerEmailAddress(String borrowerEmailAddress);

    List<Borrowing> findByBorrowerEmailAddress(String borrowerEmailAddress);

}
