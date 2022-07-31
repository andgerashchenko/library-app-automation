package com.cydeo.library.pages;

import com.cydeo.library.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UsersPage extends BasePage {
    public UsersPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(partialLinkText = "Add User")
    public WebElement addUserBtn;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement saveChangesBtn;

    @FindBy(xpath = "//input[@name='full_name']")
    public WebElement fullName;

    @FindBy(xpath = "//input[@name='password']")
    public WebElement password;

    @FindBy(xpath = "//input[@name='email']")
    public WebElement email;

    @FindBy(css = "textarea#address")
    public WebElement address;

    @FindBy(xpath = "//div[@class='toast-message']/..")
    public WebElement popUp;

}