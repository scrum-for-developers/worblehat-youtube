package de.codecentric.psd.worblehat.acceptancetests.adapter;

import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL;

import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.HtmlBookList;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.PageElement;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.LocalServerPort;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.lifecycle.TestDescription;

/** Itegrates Selenium into the tests. */
public class SeleniumAdapter {

  private static final SimpleDateFormat SIMPLE_DATE_FORMAT =
      new SimpleDateFormat("yyyy-MM-dd HH:mm");
  @LocalServerPort private int port;

  private static final Logger LOGGER = LoggerFactory.getLogger(SeleniumAdapter.class);

  private WebDriver driver;

  private String folderName;

  public void setDriver(WebDriver driver) {
    this.driver = driver;
  }

  //    @ClassRule - not supported by Cucumber at this point
  @SuppressWarnings("rawtypes")
  public static BrowserWebDriverContainer chromeContainer =
      new BrowserWebDriverContainer<>()
          .withCapabilities(new ChromeOptions())
          .withRecordingMode(RECORD_ALL, new File("./target/"));

  // a class that extends thread that is to be called when program is exiting
  static final Thread afterAllThread =
      new Thread() {

        public void run() {
          chromeContainer.afterTest(
              new TestDescription() {
                @Override
                public String getTestId() {
                  return "ID";
                }

                @Override
                public String getFilesystemFriendlyName() {
                  return "Worblehat-AcceptanceTests";
                }
              },
              Optional.empty());
        }
      };

  @Before
  public void setup() {
    if (!chromeContainer.isRunning()) {
      Runtime.getRuntime().addShutdownHook(afterAllThread);
      Testcontainers.exposeHostPorts(80, 8080, 9100, 9101, port);
      chromeContainer.start();
      LOGGER.info("Connect to VNC via " + chromeContainer.getVncAddress());
      try {
        Runtime.getRuntime().exec("open " + chromeContainer.getVncAddress());
      } catch (IOException e) {
        // silently fail, if it's not working – e.printStackTrace();
      }
    }
    setDriver(chromeContainer.getWebDriver());
  }

  @Before
  public void initSelenium() {
    folderName =
        "target"
            + File.separator
            + "screenshots"
            + File.separator
            + SIMPLE_DATE_FORMAT.format(new Date())
            + File.separator;
    ;
    new File(folderName).mkdirs();
  }

  public void gotoPage(Page page) {
    goToUrl(page.getUrl());
  }

  public void gotoPageWithParameter(Page page, String parameter) {
    String url = page.getUrl(parameter);
    goToUrl(url);
  }

  private void goToUrl(String url) {
    String concreteUrl = "http://host.testcontainers.internal:" + port + "/" + url;
    driver.get(concreteUrl);
    if (driver.getPageSource().contains("Whitelabel Error Page")) throw new IllegalStateException("Page could not be found: " + url);
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

  public void clickOnPageElementById(PageElement pageElement) {
    WebElement element = driver.findElement(By.id(pageElement.getElementId()));
    element.click();
  }

  public void clickOnPageElementByClassName(String className) {
    WebElement element = driver.findElement(By.className(className));
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

  @After
  public void afterAnyScenario() {
    driver.manage().deleteAllCookies();
  }

  public String getTextFromElement(PageElement pageElement) {
    WebElement element = driver.findElement(By.id(pageElement.getElementId()));
    return element.getText();
  }

  public boolean containsTextOnPage(String text) {
    return driver.getPageSource().contains(text);
  }
}
