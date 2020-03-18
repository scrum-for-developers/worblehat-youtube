Feature: Adding a new book to the library

    Scenario Outline: Adding a new book

        Given an empty library
        When a librarian adds a book with "<isbn>", "<title>", "<author>", <edition>, "<year>", and "<description>"
        Then the booklist contains a book with all the properties from the last inserted book
        And for every book the booklist contains a cover

        Examples:

            | isbn          | author          | title              | edition | year | description            |
            | 0552131075    | Terry Pratchett | Sourcery           | 1       | 1989 |                        |
            | 0552131075    | Terry Pratchett | Sourcery           | 1       | 1989 | A description          |
            | 9783827317247 | Andreas Thiel   | Komponentenmodelle | 1       | 2000 | Komponentenmodelle FTW |


    Scenario Outline: Different books must have different properties (ISBN, title, author, edition)

        Given a library, containing only one book with isbn "<isbn>"
        When a librarian tries to add a similar book with different "<title>", "<author>" and <edition>
        Then the new book "CAN NOT" be added

        Examples:

            | isbn       | author          | edition | title    |
            | 0552131075 | Jerry Pratchett | 1       | Sourcery |
            | 0552131075 | Terry Pratchett | 1       | Mastery  |
            | 0552131075 | Terry Pratchett | 2       | Sourcery |

    Scenario: Multiple copies of the same book must share common properties (ISBN, title, author, edition)

        Given a library, containing only one book with isbn "0552131075"
        When a librarian tries to add a similar book with same title, author and edition
        Then the new book "CAN" be added
        And for every book the booklist contains a cover


    Scenario: Whitespace is trimmed on book creation

        Given an empty library
        # Note the whitespace at the end of parameters in the following step
        When a librarian adds a book with " 9783827317247  ", "Komponentenmodelle    ", "Andreas Thiel   ", 1, "2000      ", and "Komponentenmodelle FTW      "
        Then the booklist contains a book with "9783827317247", "Komponentenmodelle", "Andreas Thiel", 1, "2000", and "Komponentenmodelle FTW"
