Feature: Using a book's details page

    Scenario: Details page is available

        Given a library, containing only one book with isbn "0552131075"
        And I browse the list of all books
        When I navigate to the detail page of the book with the isbn "0552131075"
        Then I can see all book details for that book
