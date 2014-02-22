Meta:
@themes Book

Narrative:
In order to take a book home for further reading
As a user
I want to borrow an interesting book.

Scenario:

Given a library with only a single unborrowed book with <isbn>

When user <user> borrows the book <isbn>

Then the book <isbn> is not available for borrowing anymore
And the user <user> has borrowed the book <isbn>

Examples:
 
| isbn       | user         |
| 1234567962 | user@test.ca |
