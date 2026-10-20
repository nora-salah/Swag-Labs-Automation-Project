package Tests;

import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Pages.P03_CartPage;
import Pages.P04_CheckoutPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Utility;
import com.github.javafaker.Faker;
import com.github.javafaker.University;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.getPropertyValue;

import Listeners.ITestResultListenerClass;
import Listeners.IInvokedMethodListenersClass;


@Listeners({IInvokedMethodListenersClass.class, ITestResultListenerClass.class})

public class TC04_CheckoutTest {

    private final String  USERNAME= DataUtils.getJsonData("ValidLogin","Username");
    private final   String  PASSWORD=DataUtils.getJsonData("ValidLogin","Password");

private final String  FIRSTNAME=DataUtils.getJsonData("information","fName")+"_"+ Utility.getTimestamp();
private final String  LASTNAME=DataUtils.getJsonData("information","lName")+"_"+ Utility.getTimestamp();
final String  ZIPCODE=new  Faker().number().digits(5);

    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment","Browser"));
        LogsUtils.info("Edge driver is opened");
        getDriver().get(getPropertyValue("environment","BASE_URL"));
        LogsUtils.info("Page  is redirected to the URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    @Test
    public void checkoutStepOneTC() throws IOException {

        //TODO: Login Steps
        new P01_LoginPage(getDriver())
                .enterUserName(USERNAME)
                .enterPassword(PASSWORD)
                .clickLoginButton();

        //TODO: Adding products
        new P02_LandingPage(getDriver())
                .addRandomProducts(2, 6)
                .clickOnCartButton();

        //TODO: Go to checkout page steps
        new P03_CartPage(getDriver())
                .clickOnCheckoutButton();

        //TODO: Filling Information Steps
        new P04_CheckoutPage(getDriver())
                .fillingInformationForm(FIRSTNAME, LASTNAME, ZIPCODE)
                .clickOnContinueButton();

        Assert.assertTrue(
                Utility.verifyUrl(getDriver(), getPropertyValue("environment", "CHECKOUT_URL"))
        );
    }

    @AfterMethod
    public void quit(){
        quitDriver();
    }
}

