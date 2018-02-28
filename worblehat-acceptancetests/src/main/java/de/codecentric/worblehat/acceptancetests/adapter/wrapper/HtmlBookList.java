package de.codecentric.worblehat.acceptancetests.adapter.wrapper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlBookList {
    private Map<String, HtmlBook> values;

    public HtmlBookList(WebElement table){
        WebElement tbody = table.findElement(By.tagName("tbody"));
        extractValues(tbody);
    }

    private void extractValues(WebElement tbody) {
        values = new HashMap<>();
        for (WebElement row : tbody.findElements(By.tagName("tr"))){
            List<WebElement> colums = row.findElements(By.tagName("td"));
            HtmlBook htmlBook = new HtmlBook(
                    colums.get(0).getText(), //Title
                    colums.get(1).getText(), //Author
                    colums.get(2).getText(), //Year
                    colums.get(3).getText(), //Edition
                    colums.get(4).getText(), //ISBN
                    colums.get(5).getText());//Borrower
            // TODO: avoid magic numbers to prevent inserted columns to break tests
            values.put(htmlBook.getIsbn(), htmlBook);
        }
    }

    public int size(){
        return values.size();
    }

    public HtmlBook getBookByIsbn(String isbn){
        return values.get(isbn);
    }

    public Map<String, HtmlBook> getHtmlBooks(){
        return values;
    }


}
