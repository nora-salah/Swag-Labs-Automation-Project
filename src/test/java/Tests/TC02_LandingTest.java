package Tests;

import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import Listeners.ITestResultListenerClass;
import Listeners.IInvokedMethodListenersClass;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.getPropertyValue;
import static Utilities.Utility.getAllCookies;
import static Utilities.Utility.restoreSession;

@Listeners({IInvokedMethodListenersClass.class, ITestResultListenerClass.class})

public class TC02_LandingTest {
    private final String  USERNAME= DataUtils.getJsonData("ValidLogin","Username");
    private final   String  PASSWORD=DataUtils.getJsonData("ValidLogin","Password");
    private Set<Cookie> cookies;


    @BeforeClass
    public void login() throws IOException {

        setupDriver(getPropertyValue("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("Page is redirected to the URL");
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));

        new P01_LoginPage(getDriver())
                .enterUserName(USERNAME)
                .enterPassword(PASSWORD)
                .clickLoginButton();

        cookies = getAllCookies(getDriver());
        quitDriver();
    }


    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment", "BASE_URL"));
        LogsUtils.info("Page is redirected to the URL");
        restoreSession(getDriver(), cookies);
        getDriver().get(getPropertyValue("environment", "HOME_URL"));
        getDriver().navigate().refresh();
    }


    @Test
    public void checkingNumberOfSelectedProductsTC()   {
        new P02_LandingPage(getDriver()).addAllProductsToCart();


        Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductsWithCart());
    }

    @Test
    public void addingRandomProductsToCartTC()   {
        new P02_LandingPage(getDriver())
                .addRandomProducts(3,6);

        Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductsWithCart());
    }
    @Test
    public void clickOnCartIconTC() throws IOException {
        new P02_LandingPage(getDriver()).clickOnCartButton();

        Assert.assertTrue(Utility.verifyUrl(getDriver(),DataUtils.getPropertyValue("environment","CART_URL")));
    }


    @AfterMethod
    public  void quit() {
        quitDriver();
    }

    @AfterClass
    public void deleteSession() {
        cookies.clear();
    }
}
