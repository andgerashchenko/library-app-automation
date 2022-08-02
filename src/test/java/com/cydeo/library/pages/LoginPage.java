package com.cydeo.library.pages;

import com.cydeo.library.step_definitions.LibraryConstants;
import com.cydeo.library.utilities.BrowserUtils;
import com.cydeo.library.utilities.Driver;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.FileInputStream;

public class LoginPage extends BasePage{
    public LoginPage (){
        PageFactory.initElements(Driver.getDriver(), this);
    }
    @FindBy(css = "input#inputEmail")
    public WebElement emailInput;

    @FindBy(css = "input#inputPassword")
    public WebElement passwordInput;

    @FindBy(css = "button")
    public WebElement loginBtn;

    @FindBy(xpath = "//div[@role='alert']")
    public WebElement alert;



    public void user_login (String userType) throws Exception {
        String path = "b26_Library.xlsx";
        FileInputStream fileInputStream = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheet("LogInData");
        LoginPage loginPage = new LoginPage();
        switch (userType.toLowerCase()){
            case LibraryConstants.LIBRARIAN:
                BrowserUtils.waitForClickablility(loginPage.loginBtn, LibraryConstants.TIME_OUT_FOR_PAGE_LOAD);
                int row = (int)(Math.random() * 61 + 1);
                loginPage.emailInput.sendKeys(sheet.getRow(row).getCell(3).toString());
                loginPage.passwordInput.sendKeys(sheet.getRow(row).getCell(4).toString());
                break;
            case LibraryConstants.STUDENT:
                BrowserUtils.waitForClickablility(loginPage.loginBtn, LibraryConstants.TIME_OUT_FOR_PAGE_LOAD);
                int row1 = (int)(Math.random() * 61 + 1);
                loginPage.emailInput.sendKeys(sheet.getRow(row1).getCell(0).toString());
                loginPage.passwordInput.sendKeys(sheet.getRow(row1).getCell(1).toString());
                break;
            default:
                throw new Exception("There is no such a "+ userType + " user type");
        }
    }
}