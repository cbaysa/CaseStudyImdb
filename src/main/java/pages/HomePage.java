package pages;

import base.BasePageMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;


public class HomePage extends BasePageMethods {


    @FindBy(how = How.XPATH, using = "//label[@id='imdbHeader-navDrawerOpen--desktop']//div[contains(text(),'Menu')]")
    private WebElement menuBtn;

    @FindBy(how = How.XPATH, using = "//label[span[text()='Awards & Events']]/following-sibling::div//a[span[text()='Oscars']]")
    private WebElement oscarsLink;

    @FindBy(how = How.ID, using = "suggestion-search")
    private WebElement searchBar;

    @FindBy(how = How.XPATH, using = "//ul[contains(@class,'react-autosuggest__suggestions-list')]//div[contains(@class,'constTitle')]")
    private List<WebElement> suggestionList;


    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickMenu() {
        try {
            clickWebElement(menuBtn);
            log.info("Menu Button has been clicked");
        } catch (Exception e) {
            Assert.fail("Error occured while clicking Menu Button!!!", e);
        }
    }

    public String clickOscars() {
        String oscarUrl = null;
        try {
            oscarUrl = oscarsLink.getAttribute("href");
            clickWebElement(oscarsLink);
            log.info("Oscars link under Awards&Events has been clicked");
        } catch (Exception e) {
            Assert.fail("Error occured while clicking Oscars link under Awards&Events!!!", e);
        }
        return oscarUrl;
    }

    public void findMovieFromSearchBarAndClick(String name, String year) {
        try {
            sendKeysWithDelay(name, searchBar);
            for (int i = 0; i < suggestionList.size(); i++) {
                WebElement option = suggestionList.get(i);
                String yearInfo = option.findElement(By.xpath(".//following-sibling::div[1]")).getText().trim();
                if (option.getText().trim().equals(name) && yearInfo.equals(year)) {
                    clickWebElement(option);
                    break;
                }

            }
            log.info("Movie {} clicked from search results", name);
        } catch (Exception e) {
            Assert.fail("Error occured while finding movie from search bar!!!", e);
        }
    }


}