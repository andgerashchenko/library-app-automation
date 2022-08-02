package com.cydeo.library.pages;

import com.cydeo.library.step_definitions.LibraryConstants;
import com.cydeo.library.utilities.BrowserUtils;
import com.cydeo.library.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public abstract class BasePage {
    public BasePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//li[@class='nav-item']")
    public List<WebElement> listOfModules;


    public void clickButton(String button, String page) throws Exception {
        switch (page.toLowerCase()) {
            case LibraryConstants.LOGIN:
                LoginPage loginPage = new LoginPage();
                BrowserUtils.waitForClickablility(loginPage.loginBtn, LibraryConstants.TIME_OUT_FOR_PAGE_LOAD);
                loginPage.loginBtn.click();
                break;
            case LibraryConstants.USERS:
                UsersPage usersPage = new UsersPage();
                button.equalsIgnoreCase("+Add User");
                BrowserUtils.waitForClickablility(usersPage.addUserBtn, LibraryConstants.TIME_OUT_FOR_PAGE_LOAD);
                usersPage.addUserBtn.click();
                break;
            case LibraryConstants.FORM:
                UsersPage usersPage1 = new UsersPage();
                BrowserUtils.waitForClickablility(usersPage1.saveChangesBtn, LibraryConstants.TIME_OUT_FOR_PAGE_LOAD);
                usersPage1.saveChangesBtn.click();
                break;
            case LibraryConstants.BOOKS:
                BooksPage booksPage = new BooksPage();
                BrowserUtils.waitForClickablility(booksPage.addBookBtn, LibraryConstants.TIME_OUT_FOR_PAGE_LOAD);
                booksPage.addBookBtn.click();
                break;
            default:
                throw new Exception("There is no such a " + page + " page");
        }
    }

    public void clickModule(String module) {
        for (WebElement element : listOfModules) {
            if (element.getText().equalsIgnoreCase(module)) {
                element.click();
            }
        }

    }
}