package Tests;

import Listeners.ITestResultListenerClass;
import Listeners.IInvokedMethodListenersClass;

import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Pages.P03_CartPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.getPropertyValue;
@Listeners({IInvokedMethodListenersClass.class, ITestResultListenerClass.class})

public class TC03_CartTest {

    private final String  username= DataUtils.getJsonData("ValidLogin","Username");
    private final   String  password=DataUtils.getJsonData("ValidLogin","Password");



    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment","Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment","BASE_URL"));
        LogsUtils.info("Page  is redirected to the URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void ComparingPricesTC() {
      String totalPrice= new P01_LoginPage(getDriver()).enterUserName(username)
              .enterPassword(password).clickLoginButton()
              .addRandomProducts(2,6)
              .getTotalPriceOfSelectedProducts();
      new P02_LandingPage(getDriver()).clickOnCartButton();
        Assert.assertTrue(new P03_CartPage(getDriver()).comparingPrice(totalPrice));
    }

    @AfterMethod
    public void quit(){
        quitDriver();
    }
}
