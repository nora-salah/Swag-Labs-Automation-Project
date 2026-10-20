package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Set;

import static Utilities.Utility.generalWait;

public class P02_LandingPage {
    private final By  addToCartButtonForAllProducts=By.xpath("//button[@class]");
    private final By numberOfProductsOcnCartIcon = By.className("shopping_cart_badge");
    private final By numberOfSelectedProducts = By.xpath("//button[.='Remove']");
    private final By  cartIcon=By.className("shopping_cart_link");
    private final By  pricesOfSelectedProductsLocator=By.xpath("//button[.='Remove']/preceding-sibling::div[@class='inventory_item_price']");
    //shopping_cart_badge
    private static List<WebElement> allProducts;
    private static List<WebElement> selectedProducts;
    static float  totalPrice=0;

    private WebDriver driver;
    public P02_LandingPage(WebDriver driver) {
        this.driver = driver;
    }
    public By getNumberOfSelectedProductsOnCart() {
        return numberOfSelectedProducts;
    }

    public P02_LandingPage addAllProductsToCart() {
        allProducts = driver.findElements(addToCartButtonForAllProducts); // 6
        LogsUtils.info("Number  of  All products: "+allProducts.size());
        for (int  i=1;i<=allProducts.size();i++) {
            By  addToCartButtonForAllProducts=By.xpath("(//button[@class])[" +i+ "]");
            Utility.clickOnElement(driver, addToCartButtonForAllProducts);
        }
        return this;
    }
    public String getNumberOfProductsOnCartIcon() {
        try {
            return Utility.getText(driver, numberOfProductsOcnCartIcon);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }
    public String getNumberOfSelectedProducts() {
        try {
            selectedProducts = driver.findElements(numberOfSelectedProducts);
            LogsUtils.info("Number of selected products: "+selectedProducts.size());
            return String.valueOf(selectedProducts.size());
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }

    public boolean comparingNumberOfSelectedProductsWithCart() {
        return getNumberOfProductsOnCartIcon().equals(getNumberOfSelectedProducts());
    }
    public P02_LandingPage addRandomProducts( int numberOfProductsNeeded,
                                              int totalNumberOfProducts){
        Set<Integer> randomNumbers=Utility.generateUniqueNumber(numberOfProductsNeeded,totalNumberOfProducts);

        for (int random:randomNumbers) {
            LogsUtils.info("Random number: "+random);
            By  addToCartButtonForAllProducts=By.xpath("(//button[@class])[" +random+ "]");
            Utility.clickOnElement(driver, addToCartButtonForAllProducts);

        }
        return this;
    }
    public P03_CartPage clickOnCartButton() {
        Utility.clickOnElement(driver, cartIcon);
        return new P03_CartPage(driver);

    }

    public String getTotalPriceOfSelectedProducts() {
        try {
            List<WebElement> pricesOfSelectedProducts = driver.findElements(pricesOfSelectedProductsLocator);
            for (int i = 1; i <= pricesOfSelectedProducts.size(); i++) {
                By elements = By.xpath("(//button[.=\"Remove\"]//preceding-sibling::div[@class='inventory_item_price'])[" + i + "]");
                String fullText = Utility.getText(driver, elements);
                totalPrice += Float.parseFloat(fullText.replace("$", ""));
            }
            LogsUtils.info("Total Price " + totalPrice);
            return String.valueOf(totalPrice);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }
   // By elements=By.xpath("//button[.='Remove']/preceding-sibling::div[@class='inventory_item_price']");

}
