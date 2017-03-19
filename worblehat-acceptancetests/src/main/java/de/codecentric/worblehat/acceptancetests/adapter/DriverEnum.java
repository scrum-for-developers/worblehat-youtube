package de.codecentric.worblehat.acceptancetests.adapter;

import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum DriverEnum {

    CHROME, PHANTOMJS;

    private Logger LOGGER = LoggerFactory.getLogger(DriverEnum.class);
    private static String DRIVER_PATH = "src/main/resources/webdriver/";
    private static String CHROMEDRIVER_MAC = "chromedriver_mac64";
    private static String CHROMEDRIVER_WIN = "chromedriver.exe";
    private static String CHROMEDRIVER_LINUX = "chromedriver_linux64";

    WebDriver getDriver() throws Exception {
        if (name().equals(CHROME.name())) {
            return createChromeDriver();
        }
        return createPhantomJSDriver();
    }

    private WebDriver createPhantomJSDriver() {
        return null;
    }

    private WebDriver createChromeDriver() throws Exception {
        StringBuilder sb = new StringBuilder(DRIVER_PATH);
        if (SystemUtils.IS_OS_MAC) {
            sb.append(CHROMEDRIVER_MAC);
        } else if (SystemUtils.IS_OS_WINDOWS) {
            sb.append(CHROMEDRIVER_WIN);
        } else if (SystemUtils.IS_OS_LINUX) {
            sb.append(CHROMEDRIVER_LINUX);
        } else {
            throw new Exception("Unsupported operation system for chromedriver");
        }
        System.setProperty("webdriver.chrome.driver", sb.toString());
        WebDriver driver = new ChromeDriver();
        assertNotNull(driver);
        return new ChromeDriver();
    }

}
