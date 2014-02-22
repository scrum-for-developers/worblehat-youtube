Meta:
@themes Book

Narrative:
In order to add new books to the library
As a librarian
I want to add books through the website

Scenario:

Given an empty library
When a book with ISBN <isbn> is added
Then the library contains only the book with <isbn>

Examples:
 
| isbn       |
| 1234567962 |






