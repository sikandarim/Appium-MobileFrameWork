package stepdefs;

import io.cucumber.java.en.When;
import pages.ShoppingCart;

public class ShoppingCartSteps {
    ShoppingCart shopping = new ShoppingCart();

    @When("I click on the shopping cart icon")
    public void clickOnShoppingCart() {
        shopping.clickShopping();
    }
}
