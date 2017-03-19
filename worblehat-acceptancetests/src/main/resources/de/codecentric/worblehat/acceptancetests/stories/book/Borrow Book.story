Meta:
@themes Book

Narrative:
In order to take a book home for further reading
As a user
I want to borrow an interesting book.

Scenario:

Given a library, containing a book with isbn <isbn>

When user <borrower> borrows the book <isbn>
Then the booklist lists the user <borrower> as borrower for the book with isbn <isbn>
And I get an error message <message> when the borrower <borrower> tries to borrow the book with isbn <isbn> again

Examples:

| isbn       | borrower      | message                       |
| 0552131075 | user@test.com | An internal error occurred    |

