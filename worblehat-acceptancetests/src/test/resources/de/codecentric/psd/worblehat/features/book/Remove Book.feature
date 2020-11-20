Feature: Remove a copy of a book from the library

    @Focus
    Scenario Outline: Remove a book

        Given a library, containing only one book with isbn "123456789X"
        And "user1@discworld.dw" has borrowed books "<user1_isbns>"

        When a librarian removes book "<removed_isbns>"

        Then the library contains <number_of_books> books with "123456789X"
        And the library still contains all borrowed books "<user1_isbns>"

        Examples:

            | user1_isbns | removed_isbns | number_of_books |
            |             | 123456789X    | 0               |
            | 9999999999  | 123456789X    | 0               |
            | 123456789X  | 123456789X    | 1               |
            | 9999999999  | 9999999999    | 1               |
            | 9999999999  | 8888888888    | 1               |
