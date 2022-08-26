package pages;

import base.BasePageMethods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;


public class MoviePage extends BasePageMethods {

    @FindBy(how = How.XPATH, using = "//div[@data-testid='title-pc-wide-screen']//li[span[contains(text(),'Director')]]//a")
    private WebElement director;

    @FindBy(how = How.XPATH, using = "//div[@data-testid='title-pc-wide-screen']//li[span[contains(text(),'Writer')]]//a")
    private List<WebElement> writerList;

    @FindBy(how = How.XPATH, using = "//div[@data-testid='title-pc-wide-screen']//li[a[contains(text(),'Stars')]]/div//li/a")
    private List<WebElement> starsList;

    @FindBy(how = How.ID, using = "home_img_holder")
    private WebElement homePageLink;

    @FindBy(how = How.XPATH, using = "//a[h3[contains(text(),'Photos')]]")
    private WebElement photosLink;

    @FindBy(how = How.XPATH, using = "//ul[@data-testid='hero-title-block__metadata']//a[contains(@href,'releaseinfo')]")
    private WebElement yearLink;

    public MoviePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String getDirectorName() {
        waitUntilElementVisible(director);
        return director.getText().trim();
    }

    public List<String> getWriterNameList() {
        List<String> writerNameList = new ArrayList<>();
        for (int i = 0; i < writerList.size(); i++) {
            writerNameList.add(writerList.get(i).getText());
        }
        return writerNameList;
    }

    public List<String> getStarsList() {
        List<String> starNameList = new ArrayList<>();
        for (int i = 0; i < starsList.size(); i++) {
            starNameList.add(starsList.get(i).getText());
        }
        return starNameList;
    }

    public String getYear() {
        return yearLink.getText().trim();
    }

    public void clickHomePageLink() {
        try {
            clickWebElement(homePageLink);
            log.info("Home Page link has been clicked");
        } catch (Exception e) {
            Assert.fail("Error occured while clicking Home Page Link!!!", e);
        }
    }

    public String clickPhotosLink() {
        String url = null;
        try {
            url = photosLink.getAttribute("href");
            clickWebElement(photosLink);
            log.info("Photos link has been clicked");
        } catch (Exception e) {
            Assert.fail("Error occured while clicking Photos Link!!!", e);
        }
        return url;
    }


}