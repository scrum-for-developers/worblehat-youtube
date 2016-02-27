-- liquibase formatted sql

-- changeset action:create_table_books
CREATE TABLE book (
  id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  title VARCHAR(255),
  author VARCHAR(255),
  edition VARCHAR(255),
  isbn VARCHAR(255),
  year_of_publication INT,
  UNIQUE (isbn)
);

-- changeset action:create_table_borrowing
CREATE TABLE borrowing(
  id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  borrow_date DATE,
  borrower_email_address VARCHAR(255),
  borrowed_book_id BIGINT,
  FOREIGN KEY (borrowed_book_id)
    REFERENCES book(id)
    ON DELETE CASCADE
);

-- changeset action:insert_demo_data
INSERT INTO book(title, author, edition, isbn, year_of_publication)
VALUES
  ("Harry Potter and the Philisopher's Stone", "J.K. Rowling", "", "0747532699", 1997),
  ("Harry Potter and the Prisoner of Azkaban", "J.K. Rowling", "", "0747542155", 1999),
  ("Harry Potter and the Goblet of Fire", "J.K. Rowling", "", "074754624X", 2000),
  ("Harry Potter and the Order of the Phoenix", "J.K. Rowling", "", "0747551006", 2003),
  ("Harry Potter and the Half-Blood Prince", "J.K. Rowling", "", "0747581088", 2005),
  ("Harry Potter and the Deathly Hallows", "J.K. Rowling", "", "0545010225", 2007);
