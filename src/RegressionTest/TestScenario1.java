package RegressionTest;

import ReusableComponents.Logging;
import org.openqa.selenium.WebDriver;
import ReusableComponents.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import ReusableFunctions.*;

public class TestScenario1 {



    public static void main(String[] args) {
        //Scenario: To test that clicking on the car category will bring me to the car category page
        //Expected results: url=http://www.mudah.my/Malaysia/Cars-for-sale-1020
        
        String expectedResults = "http://www.mudah.my/Malaysia/Cars-for-sale-1020";
        
        //Test Steps
        //Pre-condition: Initialize
        Common.initSystem();
        Logging.setLogFolder("D:/Automation_Log");
        GlobalVariable.ObjectRepoActions.loadingObjectRepository(
                "D:/eclipseWorkspace/testFramework/src/GlobalVariable/ObjectRepo.xls");
        //Test Step 1: Navigate to main page
        WebDriver wd = new FirefoxDriver();
        MudahMainPage.launchMainPage(wd);
        //Test Step 2: Click on Car
        MudahMainPage.clickOnCarsCategory(wd);
        //Test Step 3: Verify url
        Common.sleep(2);
            //screenshot for proof
        String actualResults = wd.getCurrentUrl();
        if(actualResults.equals(expectedResults)){
            Logging.takeScreen("Pass_Scenario1");
            System.out.println("Scenario 1 passed.");
        }
        else{
            Logging.takeScreen("Fail_Scenario1");
            System.out.println("Scenario 1 failed.");
        }
        //Clean up
            //Close browser
        wd.quit();
            //Close log
        Logging.closeLog();
    }
    
}
