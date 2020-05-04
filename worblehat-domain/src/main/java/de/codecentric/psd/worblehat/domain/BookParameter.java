package de.codecentric.psd.worblehat.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BookParameter {

  @NonNull private final String title;

  @NonNull private final String author;

  @NonNull private final String edition;

  @NonNull private final String isbn;

  private final int yearOfPublication;

  private String description = "";

  public BookParameter(
      String title,
      String author,
      String edition,
      String isbn,
      int yearOfPublication,
      String description) {
    this(title, author, edition, isbn, yearOfPublication);
    this.description = description;
  }
}
