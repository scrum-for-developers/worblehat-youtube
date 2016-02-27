package de.codecentric.psd.atdd.step.page;

import de.codecentric.psd.atdd.adapter.wrapper.Page;
import de.codecentric.psd.atdd.adapter.wrapper.PageElement;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.google.inject.Inject;

import de.codecentric.psd.atdd.adapter.Config;
import de.codecentric.psd.atdd.adapter.SeleniumAdapter;

public class ReturnAllBooks {
	private final SeleniumAdapter seleniumAdapter;

	@Inject
	public ReturnAllBooks(SeleniumAdapter seleniumAdapter) {
		this.seleniumAdapter = seleniumAdapter;
	}
	
	// *******************
	// *** G I V E N *****
	// *******************

	// *****************
	// *** W H E N *****
	// *****************
	
	@When("borrower <borrower1> returns all his books")
	public void whenUseruserReturnsAllHisBooks(@Named("borrower1") String borrower1) throws InterruptedException{
		seleniumAdapter.gotoPage(Page.RETURNBOOKS);
		seleniumAdapter.typeIntoField("emailAddress", borrower1);
		seleniumAdapter.clickOnPageElement(PageElement.RETURNALLBOOKSBUTTON);
	}

}
