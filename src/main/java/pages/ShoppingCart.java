package pages;

import base.Base;
import config.LocatorFactory;

public class ShoppingCart extends Base {

    public void clickShopping() {
        driver.findElement(LocatorFactory.get("shoppingCart")).click();
    }

}
