package pages;

import base.BasePageMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class EventPage extends BasePageMethods {

    @FindBy(how = How.XPATH, using = "//div[div[text()='Honorary Award']]//div[@class='event-widgets__nominees']")
    private WebElement honoraryAwardSection;


    public EventPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String selectMovieFromHonoraryAwardSection(String movieName) {
        String movieUrl = null;
        try {
            WebElement movieLink = honoraryAwardSection.findElement(By.xpath("//a[contains(text(),'" + movieName + "')]"));
            String movieTitle = movieLink.getText().trim();
            movieUrl = movieLink.getAttribute("href");
            if (movieTitle.equalsIgnoreCase(movieName) ) {
                clickWebElement(movieLink);
            } else {
                Assert.fail("Movie  " + movieName + "  could not be found on page!!");
            }
            log.info("Movie {} {} has been selected from Honorary Award Section", movieName);
        } catch (Exception e) {
            Assert.fail("Error occured while selecting Movie from Honorary Award Section!!", e);
        }
        return movieUrl;
    }


}
