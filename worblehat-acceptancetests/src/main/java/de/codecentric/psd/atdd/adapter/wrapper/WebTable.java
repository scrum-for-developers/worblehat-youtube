package de.codecentric.psd.atdd.adapter.wrapper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebTable {

    private List<String> titles;
    private List<Map<String, String>> values;

    public WebTable(WebElement table){
        WebElement thead = table.findElement(By.tagName("thead"));
        WebElement tbody = table.findElement(By.tagName("tbody"));
        extractTitleCells(thead);
        extractValues(tbody);
    }

    private void extractValues(WebElement tbody) {
        values = new ArrayList<>();
        for (WebElement row : tbody.findElements(By.tagName("tr"))){
            List<WebElement> colums = row.findElements(By.tagName("td"));
            Map<String, String> rowValues = new HashMap<>();
            for (int i = 0; i < titles.size(); i++){
                rowValues.put(titles.get(i), colums.get(i).getText());
            }
            values.add(rowValues);
        }
    }

    private void extractTitleCells(WebElement thead) {
        List<WebElement> titleCells= thead.findElements(By.tagName("td"));
        titles = new ArrayList<>();
        for (WebElement titleCell : titleCells){
            titles.add(titleCell.getText());
        }
    }

    public List<Map<String, String>> getContent(){
        return values;
    }


}
