package com.cydeo.library.step_definitions;

import com.cydeo.library.pages.DashboardPage;
import com.cydeo.library.utilities.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DBStepDefs {
    static List<String> actualAllColumnNames;
    static int actualBorrowedBooksCount;
    @Given("Establish the database connection")
    public void establish_the_database_connection() {
    }

    @When("Execute query to get all IDs from users")
    public void execute_query_to_get_all_i_ds_from_users() {
        DB_Util.runQuery("select id from users;");
    }
    @Then("verify all users has unique ID")
    public void verify_all_users_has_unique_id() {
        List<String> idList  = DB_Util.getColumnDataAsList(1);
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
        Assert.assertEquals("Column names are not equal",expectedAllColumnsNames, actualAllColumnNames);
    }

    @When("the librarian gets borrowed books number")
    public void theLibrarianGetsBorrowedBooksNumber() {
        actualBorrowedBooksCount=Integer.parseInt(new DashboardPage().borrowedBooks.getText());
    }

    @Then("borrowed books number information must match with DB")
    public void borrowedBooksNumberInformationMustMatchWithDB() {
        DB_Util.runQuery("select * from book_borrow where is_returned=0;");
        int expectedBorrowedBookCount = DB_Util.getRowCount();
        Assert.assertEquals("Number of borrowed book does not match", expectedBorrowedBookCount, actualBorrowedBooksCount);
    }
}
