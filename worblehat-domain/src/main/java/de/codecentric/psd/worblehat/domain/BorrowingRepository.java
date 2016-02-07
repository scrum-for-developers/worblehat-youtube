package de.codecentric.psd.worblehat.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long>, QueryDslPredicateExecutor {

}
