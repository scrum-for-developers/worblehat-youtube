Feature: Show borrowed books from a single user

    @Focus
    Scenario Outline: Show borrowed books

        Given a library, containing only one book with isbn "<isbns>"
        And "<borrower>" has borrowed books "<isbns>" on <borrowDate>
        And "<borrower>" has borrowed books "<more_isbns>" on <anotherBorrowDate>

        When "<borrower>" requests list of borrowed books

        Then the list shows the books "<isbns>" with due date <dueDate>
        And the list shows the books "<more_isbns>" with due date <anotherDueDate>
        And the list is sorted by dueDate in ascending order

        Examples:

            | borrower             | isbns      | borrowDate | dueDate    | more_isbns | anotherBorrowDate | anotherDueDate |
            | sandra@worblehat.net | 123456789X | 2020-10-01 | 2020-10-29 | 342314162X | 2020-11-01        | 2020-11-29     |
            | sandra@worblehat.net | 123456789X | 2020-11-01 | 2020-11-29 | 342314162X | 2020-10-01        | 2020-10-29     |
