package de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class HtmlBookList {
    private List<WebElement> headers;
    private Map<String, HtmlBook> books;

    public HtmlBookList(WebElement table) {

        headers = table.findElements(By.cssSelector("thead tr th"));

        WebElement tbody = table.findElement(By.tagName("tbody"));
        extractValues(tbody);
    }

    private void extractValues(WebElement tbody) {
        books = new LinkedHashMap<>();
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
                        book.setEdition(Integer.parseInt(cells.get(currentColumn).getText()));
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
                    case "Due Date":
                        book.setDueDate(LocalDate.from(ISO_LOCAL_DATE.parse(cells.get(currentColumn).getText())));
                        break;
        }
        currentColumn++;
      }

      books.put(book.getIsbn(), book);
    }
  }

  public int size() {
    return books.size();
  }

  public HtmlBook getBookByIsbn(String isbn) {
    return books.get(isbn);
  }

  public Map<String, HtmlBook> getHtmlBooks() {
    return books;
  }
}
