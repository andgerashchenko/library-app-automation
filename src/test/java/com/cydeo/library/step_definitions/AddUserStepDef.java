package com.cydeo.library.step_definitions;

import com.cydeo.library.pages.LoginPage;
import com.cydeo.library.pages.UsersPage;
import com.cydeo.library.utilities.BrowserUtils;
import com.cydeo.library.utilities.ConfigurationReader;
import com.cydeo.library.utilities.Driver;
import com.github.javafaker.Faker;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.poi.hssf.record.chart.DatRecord;
import org.junit.Assert;
import org.openqa.selenium.By;

public class AddUserStepDef {
    LoginPage loginPage = new LoginPage();
    UsersPage usersPage = new UsersPage();
    static Faker faker = new Faker();
    static String fullName = faker.name().fullName();
    static String email = faker.internet().emailAddress();
    static String password = faker.internet().password();
    static String address = faker.address().fullAddress();
    @Given("user as a {string} is on home page")
    public void user_as_a_is_on_home_page(String userType) throws Exception {
        Driver.getDriver().get(ConfigurationReader.getProperty("qa2_url"));
        loginPage.user_login(userType);
        loginPage.clickButton("login", "login");
    }
    @When("user clicks {string} module")
    public void user_clicks_module(String module) {
        BrowserUtils.waitForClickablility(loginPage.listOfModules.get(0), LibraryConstants.TIME_OUT_FOR_PAGE_LOAD);
        loginPage.clickModule(module);
    }
    @When("user fills out the form")
    public void user_fills_out_the_form() {
        usersPage.fullName.sendKeys(fullName);
        usersPage.password.sendKeys(password);
        usersPage.email.sendKeys(email);
        usersPage.address.sendKeys(address);
    }
    @Then("new user is visible in the list")
    public void new_user_is_visible_in_the_list() {
        BrowserUtils.waitFor(1);
        String actualFullName = Driver.getDriver().findElement(By.xpath("//tbody//tr[1]/td[3]")).getText();
        Assert.assertEquals("New user was not added", fullName, actualFullName);
    }
}
