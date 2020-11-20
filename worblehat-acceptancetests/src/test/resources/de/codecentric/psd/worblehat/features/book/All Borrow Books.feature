Feature: Show borrowed books from a single user

    Scenario Outline: Show borrowed books

        Given a library, containing only one book with isbn "<isbns>"
        And "<borrower>" has borrowed books "<isbns>" on <borrowDate>

        When "<borrower>" requests list of borrowed books

        Then the list shows the books "<isbns>" with due date <dueDate>

        Examples:

        | borrower | isbns | borrowDate | dueDate  |
        | sandra@worblehat.net | 123456789X | 2020-10-01 | 2020-10-29 |
