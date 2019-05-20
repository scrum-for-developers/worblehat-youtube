Meta:
@themes Book

Narrative:
In order to take a book home for further reading
As a user
I want to borrow an interesting book.

Scenario:

Given a library, containing only one book with isbn <isbn>

When user <borrower> borrows the book <isbn>
Then the booklist lists the user <borrower> as borrower for the book with isbn <isbn>

And I get an error message <message> when the borrower <borrower> tries to borrow the book with isbn <isbn> again

Examples:

| isbn       | borrower      | message                        |
| 0552131075 | user@test.com | The book is already borrowed.  |

Scenario: Borrow ignores whitespaces

Given a library, containing only one book with isbn 0552131075
!-- Note the whitespace at the start and end of parameters in the following step
When user    test@me.com       borrows the book       0552131075
Then the booklist lists the user test@me.com as borrower for the book with isbn 0552131075

