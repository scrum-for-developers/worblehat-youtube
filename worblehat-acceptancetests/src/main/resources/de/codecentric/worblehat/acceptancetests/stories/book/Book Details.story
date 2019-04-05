Meta:
@themes Book

Narrative:
In order to find an interesting book
as a user of the library
I want to see more details about books

Scenario:

Given a library, containing only one book with isbn <isbn>
And I browse the list of all books
When I navigate to the detail page of the book with the isbn <isbn>
Then I can see all book details for that book

Examples:

| isbn       |
| 0552131075 |