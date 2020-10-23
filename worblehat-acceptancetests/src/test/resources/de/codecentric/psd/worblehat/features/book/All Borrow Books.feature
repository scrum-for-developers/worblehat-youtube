Feature: Show borrowed books from a single user

    Scenario Outline: Show borrowed books

        Given a library, containing only one book with isbn "<isbns>"
        And "<borrower>" has borrowed books "<isbns>"

        When "<borrower>" requests list of borrowed books

        Then the list shows the books "<isbns>"

        Examples:

        | borrower | isbns |
        | sandra@worblehat.net | 123456789X |
