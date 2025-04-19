package pages;

import base.Base;
import config.LocatorFactory;

public class LoginPage extends Base {

    public void enterUsername(String username) {
        driver.findElement(LocatorFactory.get("username")).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(LocatorFactory.get("password")).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(LocatorFactory.get("loginBtn")).click();
    }
}
