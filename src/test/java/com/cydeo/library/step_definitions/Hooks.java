package com.cydeo.library.step_definitions;

import com.cydeo.library.utilities.DB_Util;
import com.cydeo.library.utilities.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @After
    public void teardownScenario(Scenario scenario){

        if (scenario.isFailed()){
            byte [] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }

        Driver.closeDriver();
    }
    @Before("@db")
    public void setupDB(){
        DB_Util.createConnection();
        System.out.println("CONNECTION SUCCESSFUL");
    }

    @After ("@db")
    public void destroyDB(){
        DB_Util.destroy();
        System.out.println("CONNECTION IS CLOSED");
    }

}
