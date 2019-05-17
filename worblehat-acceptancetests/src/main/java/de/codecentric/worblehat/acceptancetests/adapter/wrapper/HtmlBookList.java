package de.codecentric.worblehat.acceptancetests.adapter.wrapper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlBookList {
    private List<WebElement> headers;
    private Map<String, HtmlBook> values;

    public HtmlBookList(WebElement table) {

        headers = table.findElements(By.cssSelector("thead tr th"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        extractValues(tbody);
    }

    private void extractValues(WebElement tbody) {
        values = new HashMap<>();
        for (WebElement row : tbody.findElements(By.tagName("tr"))) {
            List<WebElement> cells = row.findElements(By.tagName("td"));

            HtmlBook book = new HtmlBook();
            int currentColumn = 0;
            for (WebElement column : headers) {
                switch (column.getText()) {
                    case "Title":
                        book.setTitle(cells.get(currentColumn).getText());
                        break;
                    case "Author":
                        book.setAuthor(cells.get(currentColumn).getText());
                        break;
                    case "Year":
                        book.setYearOfPublication(cells.get(currentColumn).getText());
                        break;
                    case "Edition":
                        book.setEdition(cells.get(currentColumn).getText());
                        break;
                    case "Borrower":
                        book.setBorrower(cells.get(currentColumn).getText());
                        break;
                    case "ISBN":
                        book.setIsbn(cells.get(currentColumn).getText());
                        break;
                    case "Description":
                        book.setDescription(cells.get(currentColumn).getText());
                        break;
                    case "Cover":
                        book.setCover(cells.get(currentColumn).findElement(By.tagName("img")).getAttribute("src"));
                        break;

                }
                currentColumn++;
            }

            values.put(book.getIsbn(), book);
        }
    }

    public int size() {
        return values.size();
    }

    public HtmlBook getBookByIsbn(String isbn) {
        return values.get(isbn);
    }

    public Map<String, HtmlBook> getHtmlBooks() {
        return values;
    }


}
