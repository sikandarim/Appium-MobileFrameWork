package stepdefs;

import io.cucumber.java.en.*;
import pages.LoginPage;

import java.util.Map;

public class LoginSteps  {

    LoginPage loginPage = new LoginPage();

    @When("I login as {string}")
    public void login(String userKey) {
        Map<String, String> credentials = loginPage.getLoginCredentials(userKey);
        loginPage.enterUsername(credentials.get("username"));
        loginPage.enterPassword(credentials.get("password"));
    }

    @And("I click on the login button")
    public void clickOnLoginButton() {
        loginPage.clickLogin();
    }
}
