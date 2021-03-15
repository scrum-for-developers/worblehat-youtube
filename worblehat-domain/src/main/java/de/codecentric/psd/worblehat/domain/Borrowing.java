package de.codecentric.psd.worblehat.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Borrowing implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NonNull @OneToOne private Book borrowedBook;

  @NonNull private String borrowerEmailAddress;

  @NonNull
  private LocalDate borrowDate;

  public LocalDate getDueDate() {
    return borrowDate.plusDays(28);
  }
}
