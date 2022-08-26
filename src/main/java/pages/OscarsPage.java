package pages;

import base.BasePageMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class OscarsPage extends BasePageMethods {

    public OscarsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String clickYear(String year) {
        String yearUrl = null;
        try {
            WebElement yearLink = getWebElement(By.linkText(year));
            yearUrl = yearLink.getAttribute("href");
            clickWebElement(yearLink);
            log.info("{}  has been clicked under Event History", year);
        } catch (Exception e) {
            Assert.fail("Error occured while clicking Event History year " + year, e);
        }
        return yearUrl;
    }


}
