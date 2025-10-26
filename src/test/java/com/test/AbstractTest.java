package com.test;

import com.google.common.util.concurrent.Uninterruptibles;
import com.utils.Config;
import com.utils.Constant;
import com.utils.listener.ListenerTestNG;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@Listeners({ListenerTestNG.class})
public class AbstractTest {
    protected WebDriver driver;

    private static Logger logger = LoggerFactory.getLogger(AbstractTest.class);

    @BeforeSuite
    public void configInit() {
        Config.initConfigProperties();
    }

    @BeforeTest
    public void setDriver(ITestContext ctx) throws MalformedURLException {
        this.driver = Boolean.parseBoolean(Config.get(Constant.SELENIUM_GRID_ENABLED)) ? launchRemoteBrowser() : launchLocalBrowser();
        ctx.setAttribute(Constant.DRIVER,this.driver);
        this.driver.manage().window().maximize();
    }


    private WebDriver launchRemoteBrowser() throws MalformedURLException {
        Capabilities capabilities = new ChromeOptions();

        if (Constant.FIREFOX_BROWSER.equalsIgnoreCase(Config.get(Constant.BROWSER))) {
            capabilities = new FirefoxOptions();
        }

        String urlFormat = Config.get(Constant.SELENIUM_GRID_URL_FORMAT);
        String urlHub = Config.get(Constant.SELENIUM_HUB_FORMAT);
        String completeURL = String.format(urlFormat, urlHub);
        logger.info("grid url: {}",completeURL);
        return new RemoteWebDriver(new URL(completeURL), capabilities);
    }
    private WebDriver launchLocalBrowser() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    @AfterTest
    public void quitBrowsers() {
        this.driver.quit();
    }
    @AfterMethod
    public void waitForTestMethods(){
        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
    }

}
