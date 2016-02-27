package de.codecentric.psd.atdd.step.page;

import de.codecentric.psd.atdd.adapter.wrapper.Page;
import de.codecentric.psd.atdd.adapter.SeleniumAdapter;
import de.codecentric.psd.atdd.adapter.wrapper.PageElement;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class BookList {

    private SeleniumAdapter seleniumAdapter;

    @Autowired
    public BookList(SeleniumAdapter seleniumAdapter){
        this.seleniumAdapter = seleniumAdapter;
    }

    @Then("The booklist contains a book with values title <title>, author <author>, year <year>, edition <edition>, isbn <isbn>")
    public void bookListContainsRowWithValues(@Named("title") final String title,
                                              @Named("author") final String author,
                                              @Named("year") final String year,
                                              @Named("edition") final String edition,
                                              @Named("isbn") final String isbn){
        seleniumAdapter.gotoPage(Page.BOOKLIST);
        List<Map<String, String>> content = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
        Map<String, String> wantedRow = createRowMap(title, author, year, edition, isbn, "");
        assertThat(content, contains(wantedRow));
    }

    @Then("The library contains no books")
    public void libraryIsEmpty(){
        seleniumAdapter.gotoPage(Page.BOOKLIST);
        List<Map<String, String>> content = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
        assertThat(content.size(), is(0));
    }

    @Then("the booklist lists the user <borrower> as borrower for the book with title <title>, author <author>, edition <edition>, year <year> and isbn <isbn>")
    public void bookListHasBorrowerForBookWithIsbn(@Named("title") final String title,
                                                   @Named("author") final String author,
                                                   @Named("year") final String year,
                                                   @Named("edition") final String edition,
                                                   @Named("isbn") final String isbn,
                                                   @Named("borrower") final String borrower){
        seleniumAdapter.gotoPage(Page.BOOKLIST);
        List<Map<String, String>> content = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
        Map<String, String> wantedRow = createRowMap(title, author, year, edition, isbn, borrower);
    }

    private HashMap<String, String> createRowMap(final String title, final String author, final String year,
                                                 final String edition, final String isbn, final String borrower) {
        return new HashMap<String, String>(){
            {
                put("Title", title);
                put("Author", author);
                put("Year", year);
                put("Edition", edition);
                put("ISBN", isbn);
                put("Borrower", borrower);
            }
        };
    }

}
