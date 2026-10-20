package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P06_FinishingOrderPage {
    private final WebDriver driver;
private final By thankMessage=By.tagName("h2");
    public P06_FinishingOrderPage(WebDriver driver) {
        this.driver=driver;

    }
    public boolean checkVisibilityOfThankMessage() {
        return driver.findElement(thankMessage).isDisplayed();
    }
}
