package com.cydeo.library.step_definitions;

import com.cydeo.library.pages.BooksPage;
import com.cydeo.library.pages.DashboardPage;
import com.cydeo.library.utilities.BrowserUtils;
import com.cydeo.library.utilities.DB_Util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.*;

public class DBStepDefs {
    static List<String> actualAllColumnNames;
    int actualBorrowedBooksCount;
    List<String> actualCategoryList;
    BooksPage booksPage = new BooksPage();
    String actualBookName;
    String actualISBN;
    String actualYear;
    String actualAuthor;
    String actualCategory;
    String borrowedBookName;

    @Given("Establish the database connection")
    public void establish_the_database_connection() {
    }

    @When("Execute query to get all IDs from users")
    public void execute_query_to_get_all_i_ds_from_users() {
        DB_Util.runQuery("select id from users;");
    }

    @Then("verify all users has unique ID")
    public void verify_all_users_has_unique_id() {
        List<String> idList = DB_Util.getColumnDataAsList(1);
        Set<String> idSet = new HashSet<>(idList);
        Assert.assertEquals("Duplicated ids found", idSet.size(), idList.size());
    }

    @When("Execute query to get all columns")
    public void executeQueryToGetAllColumns() {
        DB_Util.runQuery("select * from users");
        actualAllColumnNames = DB_Util.getAllColumnNamesAsList();
        System.out.println(actualAllColumnNames);
    }

    @Then("verify the below columns are listed in result")
    public void verifyTheBelowColumnsAreListedInResult(List<String> expectedAllColumnsNames) {
        Assert.assertEquals("Column names are not equal", expectedAllColumnsNames, actualAllColumnNames);
    }

    @When("the librarian gets borrowed books number")
    public void theLibrarianGetsBorrowedBooksNumber() {
        actualBorrowedBooksCount = Integer.parseInt(new DashboardPage().borrowedBooks.getText());
    }

    @Then("borrowed books number information must match with DB")
    public void borrowedBooksNumberInformationMustMatchWithDB() {
        DB_Util.runQuery("select * from book_borrow where is_returned=0;");
        int expectedBorrowedBookCount = DB_Util.getRowCount();
        Assert.assertEquals("Number of borrowed book does not match", expectedBorrowedBookCount, actualBorrowedBooksCount);
    }

    @And("the user clicks book categories")
    public void theUserClicksBookCategories() {
        actualCategoryList = BrowserUtils.getAllSelectOptions(booksPage.mainCategoryElement);
        actualCategoryList.remove(0);
    }

    @Then("verify book categories must match book_categories table from db")
    public void verifyBookCategoriesMustMatchBook_categoriesTableFromDb() {
        DB_Util.runQuery("select name from book_categories;");
        List<String> expectedCategories = DB_Util.getColumnDataAsList(1);
        Assert.assertEquals("Categories not matching", expectedCategories, actualCategoryList);
    }

    @When("the user searches for {string} book And the user clicks edit book button")
    public void theUserSearchesForBookAndTheUserClicksEditBookButton(String bookName) {
        booksPage.search.sendKeys(bookName, Keys.RETURN);
        BrowserUtils.waitFor(1);
        booksPage.editBookBtn.click();
        BrowserUtils.waitFor(1);
        actualBookName = booksPage.bookName.getAttribute("value");
        actualAuthor = booksPage.author.getAttribute("value");
        actualISBN = booksPage.isbn.getAttribute("value");
        actualYear = booksPage.year.getAttribute("value");
        Select select = new Select(booksPage.formMainCategoryElement);
        for (WebElement option : select.getOptions()) {
            if (option.isSelected()) {
                actualCategory = option.getText();
            }
        }
    }

    @Then("book information must match the Database")
    public void bookInformationMustMatchTheDatabase() {
        DB_Util.runQuery("select b.name, isbn, year, author, bc.name from books b inner join book_categories bc on b.book_category_id = bc.id where b.name='" + actualBookName + "'");
        List<String> expectedData = DB_Util.getRowDataAsList(1);
        List<String> actualData = new ArrayList<>(Arrays.asList(actualBookName, actualISBN, actualYear, actualAuthor, actualCategory));
        Assert.assertEquals("Book information does not match", expectedData, actualData);
    }

    @And("the user searches book name called {string}")
    public void theUserSearchesBookNameCalled(String bookName) {
        booksPage.search.sendKeys(bookName, Keys.RETURN);
        borrowedBookName = bookName;
    }


    @When("the user clicks Borrow Book")
    public void theUserClicksBorrowBook() throws Exception {
        BrowserUtils.waitFor(1);
        if (!booksPage.borrowBookBtn.getAttribute("class").contains("disabled"))
            booksPage.borrowBookBtn.click();
        else
            throw new Exception ("Book "+borrowedBookName+"is already borrowed");

    }

    @Then("verify that book is shown in the page")
    public void verifyThatBookIsShownInThePage() {
        List<WebElement> allBookNames = booksPage.allBookNames;
        boolean bookIsPresent=false;
        for (WebElement bookName : allBookNames) {
            if (bookName.getText().equals(borrowedBookName))
                bookIsPresent=true;
        }
        Assert.assertTrue(bookIsPresent);
    }

    @And("verify logged student has same book in database")
    public void verifyLoggedStudentHasSameBookInDatabase() {
        DB_Util.runQuery("select b.name\n" +
                "from book_borrow br inner join books b on br.book_id = b.id where b.name = '" + borrowedBookName + "';");
        String expectedBookName = DB_Util.getFirstRowFirstColumn();
        Assert.assertEquals(expectedBookName, borrowedBookName);
        booksPage.returnLastBook();
    }

}
