package de.codecentric.psd.worblehat.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class BorrowingTest {

    @Test
    void getDueDate() {
        var borrowingUnderTest = new Borrowing();
        var now = LocalDate.of(2020, Month.OCTOBER, 1);
        borrowingUnderTest.setBorrowDate(now);

        LocalDate dueDate = borrowingUnderTest.getDueDate();
        assertThat(dueDate, is(LocalDate.of(2020, Month.OCTOBER, 29)));
    }

}
