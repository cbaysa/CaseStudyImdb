package pages;

import base.BasePageMethods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class PhotosPage extends BasePageMethods {

    @FindBy(how = How.XPATH, using = "//div[@class='media_index_thumb_list']/a")
    private List<WebElement> photoLinks;

    @FindBy(how = How.XPATH, using = "//span[@class='page_list']")
    private List<WebElement> nextPageList;

    @FindBy(how = How.XPATH, using = "//div[@id='right']/a[@class='prevnext']")
    private WebElement next;

    public PhotosPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean checkBrokenLinks() {
        boolean check = false;
        int pageCount = nextPageList.size();
        check = checkBrokenLinksOnActivePage();
        if(pageCount>0) {
            for (int i = 1; i < pageCount; i++) {
                clickWebElement(next);
                check = checkBrokenLinksOnActivePage();
                if (check == false) {
                    break;
                }
            }
        }

        return check;
    }

    public boolean checkBrokenLinksOnActivePage() {
        String url;
        HttpURLConnection huc;
        int responseCode;
        boolean result = false;

        try {
            Iterator<WebElement> it = photoLinks.iterator();
            while (it.hasNext()) {
                url = it.next().getAttribute("href");
                huc = (HttpURLConnection) (new URL(url).openConnection());
                huc.setRequestMethod("HEAD");
                huc.connect();
                responseCode = huc.getResponseCode();
                if (responseCode >= 400) {
                    result = false;
                    break;
                }
            }
            result = true;
            log.info("Broken links not found");
        } catch (Exception e) {
            Assert.fail("Error occured while checking broken links!!", e);
        }
        return result;
    }
}

