package com.cydeo.library.step_definitions;

import com.cydeo.library.pages.LoginPage;
import com.cydeo.library.utilities.BrowserUtils;
import com.cydeo.library.utilities.ConfigurationReader;
import com.cydeo.library.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class LoginStepDef {
    LoginPage loginPage = new LoginPage();

    @Given("user is on login page")
    public void user_is_on_login_page() {
        Driver.getDriver().get(ConfigurationReader.getProperty("qa2_url"));
    }
    @Then("title of the page is {string}")
    public void title_of_the_page_is(String expectedTitle) {
        Assert.assertEquals(expectedTitle, Driver.getDriver().getTitle());
    }

    @When("user as a {string} enters valid username and password")
    public void user_logs_in_as_a_with_valid_username_and_password(String userType) throws Exception {
        loginPage.user_login(userType);
    }

    @And("user click {string} button on {string} page")
    public void userClickButtonOnPage(String button, String page) throws Exception {
        loginPage.clickButton(button, page);

    }

    @Then("user can see {int} modules on the dashboard page")
    public void user_can_see_modules_on_the_dashboard_page(Integer expectedModulesCount) {
        Integer actualModulesCount = loginPage.listOfModules.size();
        Assert.assertEquals("Incorrect numbers of modules accessible", expectedModulesCount, actualModulesCount);
    }


    @When("user enters invalid username and password")
    public void userEntersInvalidUsernameAndPassword() {
        loginPage.emailInput.sendKeys("abcd@efgh.ijk");
        loginPage.passwordInput.sendKeys("123456");
    }

    @Then("user can see error message {string}")
    public void userCanSeeErrorMessage(String expectedMessage) {
        BrowserUtils.waitForVisibility(loginPage.alert, LibraryConstants.TIME_OUT_FOR_PAGE_LOAD);
        String actualMessage = loginPage.alert.getText();
        Assert.assertEquals("Error message is not matching", expectedMessage, actualMessage);
    }
}
