package Tests;

import Listeners.ITestResultListenerClass;
import Listeners.IInvokedMethodListenersClass;

import Pages.P01_LoginPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import org.testng.Assert;
import org.testng.annotations.*;


import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.getPropertyValue;
@Listeners({IInvokedMethodListenersClass.class, ITestResultListenerClass.class})
public class TC01_LoginTest {
    private final String  username=DataUtils.getJsonData("ValidLogin","Username");
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
    public void ValidLoginTest() throws IOException {
        new P01_LoginPage(getDriver()).enterUserName(username).enterPassword(password).clickLoginButton();
        Assert.assertTrue(new P01_LoginPage(getDriver()).assertLoginTc(getPropertyValue("environment","HOME_URL")));
    }

    @AfterMethod
    public void quit(){
        quitDriver();
    }
}
