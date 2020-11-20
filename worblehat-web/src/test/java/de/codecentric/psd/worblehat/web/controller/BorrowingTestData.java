package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.Borrowing;
import java.util.Date;

public class BorrowingTestData {

  private BorrowingTestData() {}

  static Borrowing borrowingWith(String emailAddress) {
    Book aBook = new Book("Title", "Author", "1", "isbn", 2020);
    return new Borrowing(aBook, emailAddress, new Date());
  }
}
