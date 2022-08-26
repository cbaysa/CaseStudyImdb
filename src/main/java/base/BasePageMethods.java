package base;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class BasePageMethods {

    public static final Logger log = LogManager.getLogger(BasePageMethods.class);
    WebDriver driver;
    WebDriverWait webDriverWait;
    JavascriptExecutor jsExec;
    Actions builder;
    String parentWindowId;
    long timeOutInSeconds = 90L;

    public BasePageMethods(WebDriver driver) {
        this.driver = driver;
        this.webDriverWait = new WebDriverWait(this.driver, Duration.ofSeconds(90L));
        this.jsExec = (JavascriptExecutor) this.driver;
        this.builder = new Actions(this.driver);
        this.parentWindowId = parentWindowId;
        PageFactory.initElements(driver, this);
    }

    public WebElement waitUntilClickable(WebElement webElement) {
        WebElement element = null;

        try {
            Wait<WebDriver> wait = (new FluentWait(this.driver)).withTimeout(Duration.ofSeconds(this.timeOutInSeconds)).pollingEvery(Duration.ofMillis(100L)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            element = wait.until(ExpectedConditions.elementToBeClickable(webElement));
        } catch (Exception e) {
            Assert.fail("Web Element can not be clicked!!", e);
        }

        return element;
    }


    public void scrollTo(int x, int y) {
        this.jsExec.executeScript("scrollTo(" + x + "," + y + ");", new Object[0]);
    }

    public void clickWebElement(WebElement element) {
        this.scrollTo(element, 100);
        this.waitUntilClickable(element).click();
        this.waitForPageToLoad();
    }

    public void scrollTo(WebElement element, int margin) {
        this.scrollTo(element.getLocation().x, element.getLocation().y - margin);
    }

    public void checkPageHasOpened(String url) {
        waitForPageToLoad();
        if (waitUntilUrlContains(url)) {
            log.info("URL {} is opened", url);
        } else {
            Assert.fail("Error occured while opening " + url);
        }
    }

    public WebElement getWebElement(By locator) {
        return driver.findElement(locator);
    }

    public boolean waitUntilUrlContains(String expectedValue) {
        try {
            Wait<WebDriver> wait = (new FluentWait(this.driver)).withTimeout(Duration.ofSeconds(30L)).pollingEvery(Duration.ofMillis(100L)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.urlContains(expectedValue));
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }


    public void waitForJsLoad() {
        try {
            ExpectedCondition<Boolean> jsLoad = (webDriver) -> jsExec.executeScript("return document.readyState", new Object[0]).toString().equals("complete");
            String jsReadyState;
            do {
                webDriverWait.until(jsLoad);
                jsReadyState = jsExec.executeScript("return document.readyState", new Object[0]).toString();
            } while (!jsReadyState.equalsIgnoreCase("complete"));
        } catch (Exception e) {
            log.error("JS LOAD on page failed !!!");
            throw e;
        }
    }

    public void waitForPageToLoad() {
        try {
            waitForJsLoad();
        } catch (Exception e) {
            Assert.fail("Wait for page failed", e);
        }
    }

    public void waitUntilElementVisible(WebElement element) {
        try {
            FluentWait<WebDriver> wait = (new WebDriverWait(this.driver, Duration.ofSeconds(this.timeOutInSeconds))).pollingEvery(Duration.ofSeconds(5L)).withTimeout(Duration.ofSeconds(this.timeOutInSeconds)).ignoring(StaleElementReferenceException.class).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            Assert.fail("Element: " + element + " is not visible !!", e);
        }
    }


    public void sendKeysWithDelay(String keysToSend, WebElement element) {
        for (char c : keysToSend.toCharArray()) {
            element.sendKeys(Character.toString(c));
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (Exception e) {
                Assert.fail("Typing error occured", e);
            }
        }
    }
}
