package de.codecentric.worblehat.acceptancetests.adapter;

import de.codecentric.worblehat.acceptancetests.adapter.wrapper.HtmlBookList;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.PageElement;
import org.apache.commons.io.FileUtils;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.ScenarioType;
import org.joda.time.LocalDateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Itegrates Selenium into the tests.
 */
@Controller
public class SeleniumAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeleniumAdapter.class);

    private WebDriver driver;

    private String folderName;

    @BeforeStories
    public void initSelenium() {
        String seleniumProvider = Config.getEnvironment();
        try {
            if (seleniumProvider.equalsIgnoreCase("local")) {
                driver = DriverEnum.CHROME.getDriver();
            } else {
                driver = DriverEnum.PHANTOMJS.getDriver();
            }
        } catch (Exception e) {
            LOGGER.error("Error initializing Webdriver", e);
            System.out.println(e.getStackTrace());
        }

        folderName = LocalDateTime.now().toString("yyyy-MM-dd HH:mm").concat("/");
        folderName = "target/screenshots/".concat(folderName);
        new File(folderName).mkdirs();

    }

    public void gotoPage(Page page) {
        String concreteUrl = Config.getApplicationURL() + "/" + page.getUrl();
        driver.get(concreteUrl);
    }

    public void typeIntoField(String id, String value) {
        WebElement element = driver.findElement(By.id(id));
        element.clear();
        element.sendKeys(value);
    }

    public HtmlBookList getTableContent(PageElement pageElement) {
        WebElement table = driver.findElement(By.className(pageElement.getElementId()));
        return new HtmlBookList(table);
    }

    public void clickOnPageElement(PageElement pageElement) {
        WebElement element = driver.findElement(By.id(pageElement.getElementId()));
        element.click();
    }

    public List<String> findAllStringsForElement(PageElement pageElement) {
        List<WebElement> webElements = driver.findElements(By.className(pageElement.getElementId()));
        List<String> strings = new ArrayList<>();
        for (WebElement element : webElements) {
            strings.add(element.getText());
        }
        return strings;
    }

    @AfterStories
    public void afterStories() {
        // Close the browser
        driver.quit();
    }

    @AfterScenario(uponType = ScenarioType.EXAMPLE)
    public void afterAnyScenario() {
        driver.manage().deleteAllCookies();
    }

    public String getTextFromElement(PageElement pageElement) {
        WebElement element = driver.findElement(By.id(pageElement.getElementId()));
        return element.getText();
    }

    public void takeScreenShot(String filename) {

        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(folderName.concat(filename).concat(".png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
