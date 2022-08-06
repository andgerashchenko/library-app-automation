package com.cydeo.library.step_definitions;

import com.cydeo.library.pages.BooksPage;
import com.cydeo.library.utilities.BrowserUtils;
import com.cydeo.library.utilities.DB_Util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class BooksStepDef {
    @Then("new book is listed in the database")
    public void new_book_is_listed_in_the_database() {
        UsersStepDef usersStepDef = new UsersStepDef();
        DB_Util.runQuery("select isbn, b.name, author, bc.name, year from books b left join book_categories bc on b.book_category_id = bc.id where b.name = '"+ usersStepDef.bookName +"'and author='"+ usersStepDef.author+"';");
         List<String> expectedBookInfo = DB_Util.getRowDataAsList(1);
        List<String> actualBookInfo = new ArrayList<>();
        actualBookInfo.add(usersStepDef.isbn);
        actualBookInfo.add(usersStepDef.bookName);
        actualBookInfo.add(usersStepDef.author);
        actualBookInfo.add(usersStepDef.chosenCategory);
        actualBookInfo.add(usersStepDef.year);
        Assert.assertEquals(expectedBookInfo, actualBookInfo);
    }

}
