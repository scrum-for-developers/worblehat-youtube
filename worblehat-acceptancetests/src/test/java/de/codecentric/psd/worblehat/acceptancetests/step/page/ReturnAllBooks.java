package de.codecentric.psd.worblehat.acceptancetests.step.page;

import de.codecentric.psd.worblehat.acceptancetests.adapter.SeleniumAdapter;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.PageElement;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class ReturnAllBooks {
  private final SeleniumAdapter seleniumAdapter;

  @Autowired
  public ReturnAllBooks(SeleniumAdapter seleniumAdapter) {
    this.seleniumAdapter = seleniumAdapter;
  }

  // *****************
  // *** W H E N *****
  // *****************

  @When("{string} returns all books")
  public void whenUseruserReturnsAllHisBooks(String borrower1) {
    seleniumAdapter.gotoPage(Page.RETURNBOOKS);
    seleniumAdapter.typeIntoField("emailAddress", borrower1);
    seleniumAdapter.clickOnPageElementById(PageElement.RETURNALLBOOKSBUTTON);
  }
}
