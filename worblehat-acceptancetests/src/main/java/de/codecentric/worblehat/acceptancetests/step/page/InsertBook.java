package de.codecentric.worblehat.acceptancetests.step.page;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.worblehat.acceptancetests.adapter.SeleniumAdapter;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.PageElement;
import de.codecentric.worblehat.acceptancetests.step.StoryContext;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@Component
public class InsertBook {

    private final SeleniumAdapter seleniumAdapter;

    private StoryContext storyContext;

    @Autowired
    public InsertBook(SeleniumAdapter seleniumAdapter, StoryContext storyContext) {
        this.seleniumAdapter = seleniumAdapter;
        this.storyContext = storyContext;
    }

    // *******************
    // *** G I V E N *****
    // *******************

    // *****************
    // *** W H E N *****
    // *****************

    // TODO: write story that contains whitespaces in examples table

    @When("a librarian adds a book with title $title, author $author, edition $edition, year $year and isbn $isbn")
    public void whenABookIsAdded(String title,
                                 String author,
                                 String edition,
                                 String year,
                                 String isbn) {
        insertAndSubmitBook(title.replace("\"",""), author.replace("\"",""), edition.replace("\"",""), year.replace("\"",""), "", isbn.replace("\"",""));
    }

    @When("a librarian adds a book with title $title, author $author, edition $edition, year $year, description $description and isbn $isbn")
    public void whenABookIsAdded(String title,
                                 String author,
                                 String edition,
                                 String year,
                                 String description,
                                 String isbn) {
        insertAndSubmitBook(title.replace("\"",""), author.replace("\"",""), edition.replace("\"",""), year.replace("\"",""), description.replace("\"",""), isbn.replace("\"",""));
    }

    @When("a librarian tries to add a similar book with different $title, $author and $edition")
    public void whenASimilarBookIsAdded(String title,
                                        String author,
                                        String edition) {
        Book lastInsertedBook = (Book) storyContext.getObject("LAST_INSERTED_BOOK");
        insertAndSubmitBook(title,
            author,
            edition,
            String.valueOf(lastInsertedBook.getYearOfPublication()),
            lastInsertedBook.getDescription(),
            lastInsertedBook.getIsbn());
    }

    @When("a librarian tries to add a similar book with same title, author and edition")
    public void whenASimilarBookIsAdded() {
        Book lastInsertedBook = (Book) storyContext.getObject("LAST_INSERTED_BOOK");
        insertAndSubmitBook(lastInsertedBook.getTitle(),
            lastInsertedBook.getAuthor(),
            lastInsertedBook.getEdition(),
            String.valueOf(lastInsertedBook.getYearOfPublication()),
            lastInsertedBook.getDescription(),
            lastInsertedBook.getIsbn());

    }

    // *****************
    // *** T H E N *****
    // *****************

    @Then("the page contains error message for field $field")
    public void pageContainsErrorMessage(String field) {
        String errorMessage = seleniumAdapter.getTextFromElement(("isbn".equals(field) ? PageElement.ISBN_ERROR : PageElement.EDITION_ERROR));
        assertThat(errorMessage, notNullValue());
    }

    // *****************
    // *** U T I L *****
    // *****************

    private void insertAndSubmitBook(String title,
                                     String author,
                                     String edition,
                                     String year,
                                     String description,
                                     String isbn) {
        seleniumAdapter.gotoPage(Page.INSERTBOOKS);
        fillInsertBookForm(title, author, edition, isbn, year, description);
        seleniumAdapter.clickOnPageElementById(PageElement.ADDBOOKBUTTON);
    }

    private void fillInsertBookForm(String title, String author, String edition, String isbn,
                                    String year, String description) {
        seleniumAdapter.typeIntoField("title", title);
        seleniumAdapter.typeIntoField("edition", edition);
        seleniumAdapter.typeIntoField("isbn", isbn);
        seleniumAdapter.typeIntoField("author", author);
        seleniumAdapter.typeIntoField("yearOfPublication", year);
        Optional.ofNullable(description).ifPresent(desc -> seleniumAdapter.typeIntoField("description", description));
    }


}
