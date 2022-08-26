package test;

import base.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.*;

import java.util.List;



public class ImdbTests extends BaseTest {

    String directorNameFromMenu;
    List<String> writerNameFromMenu;
    List<String> starsNameFromMenu;
    String directorNameFromSearchBar;
    List<String> writerNameFromSearchBar;
    List<String> starsNameFromSearchBar;
    String year;

    @Parameters({"movie"})
    @Test
    public void checkMovieFromMenuAndSearchBar(String movie) throws InterruptedException {
        String homePageUrl = "https://www.imdb.com/";
        WebDriver drv = getWebDriver();
        drv.get(homePageUrl);
        HomePage hp = new HomePage(drv);
        hp.checkPageHasOpened(homePageUrl);
        hp.clickMenu();
        String oscarUrl = hp.clickOscars();


        OscarsPage op = new OscarsPage(drv);
        op.checkPageHasOpened(oscarUrl);
        String yearUrl = op.clickYear("1929");

        EventPage ep = new EventPage(drv);
        ep.checkPageHasOpened(yearUrl);
        String moviePageUrl = ep.selectMovieFromHonoraryAwardSection(movie);

        MoviePage mp = new MoviePage(drv);
        mp.checkPageHasOpened(moviePageUrl);
        directorNameFromMenu = mp.getDirectorName();
        writerNameFromMenu = mp.getWriterNameList();
        starsNameFromMenu = mp.getStarsList();
        year = mp.getYear();
        mp.clickHomePageLink();

        hp.checkPageHasOpened(homePageUrl);
        hp.findMovieFromSearchBarAndClick(movie,year);

        directorNameFromSearchBar = mp.getDirectorName();
        writerNameFromSearchBar = mp.getWriterNameList();
        starsNameFromSearchBar = mp.getStarsList();

        Assert.assertTrue(directorNameFromMenu.equals(directorNameFromSearchBar),"Director name on movie page that is found from Menu and director name on movie page that is found from seacrh bar are different!!!");
        Assert.assertTrue(writerNameFromMenu.equals(writerNameFromSearchBar),"Writer names on movie page that is found from Menu and writer names on movie page that is found from seacrh bar are different!!!");
        Assert.assertTrue(starsNameFromMenu.equals(starsNameFromSearchBar),"Star names on movie page that is found from Menu and star names on movie page that is found from seacrh bar are different!!!");

        String photosPageUrl = mp.clickPhotosLink();
        PhotosPage pp = new PhotosPage(drv);
        pp.checkPageHasOpened(photosPageUrl);
        Assert.assertTrue(pp.checkBrokenLinks(),"Broken photo links found!!!");
    }


}
