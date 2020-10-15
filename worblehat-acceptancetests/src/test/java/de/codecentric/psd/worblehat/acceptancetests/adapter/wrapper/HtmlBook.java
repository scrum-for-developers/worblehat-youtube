package de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper;

import java.time.LocalDate;
import java.util.Date;

public class HtmlBook {

  private String title;
  private String author;
  private Integer edition;
  private String isbn;
  private String yearOfPublication;
  private String borrower;
  private String description;
  private String cover;
  private LocalDate dueDate;

  public HtmlBook() {
    title = author = isbn = yearOfPublication = borrower = "";
    edition = 0;
  }

  public HtmlBook(
      String title,
      String author,
      String yearOfPublication,
      Integer edition,
      String isbn,
      String borrower) {
    this.title = title;
    this.author = author;
    this.edition = edition;
    this.isbn = isbn;
    this.yearOfPublication = yearOfPublication;
    this.borrower = borrower;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Integer getEdition() {
    return edition;
  }

  public void setEdition(Integer edition) {
    this.edition = edition;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getYearOfPublication() {
    return yearOfPublication;
  }

  public void setYearOfPublication(String yearOfPublication) {
    this.yearOfPublication = yearOfPublication;
  }

  public String getBorrower() {
    return borrower;
  }

  public void setBorrower(String borrower) {
    this.borrower = borrower;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public void setDueDate(LocalDate dueDate) {
      this.dueDate = dueDate;
  }

  public LocalDate getDueDate() {
      return dueDate;
  }
}
