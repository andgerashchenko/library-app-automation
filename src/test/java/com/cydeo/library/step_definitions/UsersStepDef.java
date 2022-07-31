package com.cydeo.library.step_definitions;

import com.cydeo.library.pages.BooksPage;
import com.cydeo.library.pages.LoginPage;
import com.cydeo.library.pages.UsersPage;
import com.cydeo.library.utilities.BrowserUtils;
import com.cydeo.library.utilities.ConfigurationReader;
import com.cydeo.library.utilities.Driver;
import com.github.javafaker.Faker;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class UsersStepDef {
    LoginPage loginPage = new LoginPage();
    UsersPage usersPage = new UsersPage();
    BooksPage booksPage = new BooksPage();
     Faker faker = new Faker();
     String fullName = faker.name().fullName();
     String email = faker.internet().emailAddress();
     String password = faker.internet().password();
     String address = faker.address().fullAddress();
     public static String bookName;
     public static String isbn;
     public static String year;
     public static String author;
     public static String description;
     public static String chosenCategory;

    public UsersStepDef() {
    }

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

    @When("user fills out {string} form")
    public void user_fills_out_the_form(String formType) throws Exception {
        switch (formType.toLowerCase()) {
            case "add user":
                usersPage.fullName.sendKeys(fullName);
                usersPage.password.sendKeys(password);
                usersPage.email.sendKeys(email);
                usersPage.address.sendKeys(address);
                break;
            case "add book":
                bookName = faker.book().title();
                isbn = faker.numerify("############");
                year = faker.numerify("19##");
                author = faker.book().author();
                description = faker.chuckNorris().fact();
                booksPage.bookName.sendKeys(bookName);
                booksPage.isbn.sendKeys(isbn);
                booksPage.year.sendKeys(year);
                booksPage.author.sendKeys(author);
                booksPage.description.sendKeys(description);
                List<String> CategoryList = BrowserUtils.getAllSelectOptions(booksPage.mainCategoryElement);
                int categoryNum = (int)(Math.random() * (CategoryList.size()-1)+1);
                WebElement bookCategories = Driver.getDriver().findElement(By.id("book_group_id"));
                chosenCategory = CategoryList.get(categoryNum).trim();
                Select select = new Select(bookCategories);
                select.selectByVisibleText(chosenCategory);

                break;
            default:
                throw new Exception("There is no such form");

        }
    }

    @Then("new user is visible in the list")
    public void new_user_is_visible_in_the_list() {
        BrowserUtils.waitFor(3);
        WebElement newName = Driver.getDriver().findElement(By.xpath("//tbody//tr[1]/td[3]"));
        String actualFullName = newName.getText();
        Assert.assertEquals("New user was not added", fullName, actualFullName);
        String actualEmail = Driver.getDriver().findElement(By.xpath("//tbody//tr[1]/td[4]")).getText();
        Assert.assertEquals("New user was not added", email, actualEmail);
    }
}
