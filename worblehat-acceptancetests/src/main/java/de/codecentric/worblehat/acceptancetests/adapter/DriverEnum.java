package de.codecentric.worblehat.acceptancetests.adapter;

import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public enum DriverEnum {

    CHROME, PHANTOMJS;

    private Logger LOGGER = LoggerFactory.getLogger(DriverEnum.class);

    private static String DRIVER_PATH =
            "target/binaries/";

    private static String CHROMEDRIVER_MAC = "osx/googlechrome/64bit/chromedriver";

    private static String CHROMEDRIVER_WIN = "windows/googlechrome/64bit/chromedriver.exe";

    private static String CHROMEDRIVER_LINUX = "linux/googlechrome/64bit/chromedriver";

    private static String PHANTOMJSDRIVER_MAC = "osx/phantomjs/64bit/phantomjs";

    private static String PHANTOMJSDRIVER_WIN = "windows/phantomjs/64bit/phantomjs.exe";

    private static String PHANTOMJSDRIVER_LINUX = "linux/phantomjs/64bit/phantomjs";

    WebDriver getDriver() throws Exception {
        if (name().equals(CHROME.name())) {
            return createChromeDriver();
        }
        return createPhantomJSDriver();
    }

    private WebDriver createPhantomJSDriver() throws Exception {
        StringBuilder sb = new StringBuilder(DRIVER_PATH);
        if (SystemUtils.IS_OS_MAC) {
            sb.append(PHANTOMJSDRIVER_MAC);
        } else if (SystemUtils.IS_OS_WINDOWS) {
            sb.append(PHANTOMJSDRIVER_WIN);
        } else if (SystemUtils.IS_OS_LINUX) {
            sb.append(PHANTOMJSDRIVER_LINUX);
        } else {
            throw new Exception("Unsupported operation system for chromedriver");
        }
        System.out.println("Using driver from the following path: " + sb);
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(
                PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                sb.toString());
        WebDriver driver = new PhantomJSDriver(caps);
        driver.manage().window().setSize(new Dimension(1280, 800));
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        return driver;
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
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
                sb.toString());
        System.setProperty("webdriver.chrome.driver", sb.toString());
        return new ChromeDriver(caps);
    }

}
