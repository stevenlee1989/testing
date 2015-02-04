package RegressionTest;

import ReusableComponents.Logging;
import org.openqa.selenium.WebDriver;
import ReusableComponents.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import ReusableFunctions.*;

public class TestScenario2 {



    public static void main(String[] args) {
        //Scenario: To test that clicking on the properties category will bring me to the properties category page
        //Expected results: url=http://www.mudah.my/Malaysia/Properties-for-sale-2000
        
        String expectedResults = "http://www.mudah.my/Malaysia/Properties-for-sale-2000";
        
        //Test Steps
        //Pre-condition: Initialize
        Common.initSystem();
        Logging.setLogFolder("D:/Automation_Log");
        GlobalVariable.ObjectRepoActions.loadingObjectRepository(
                "/Users/chong/NetBeansProjects/CackleTestFramework_v0.1/src/GlobalVariable/ObjectRepo.xls");
        //Test Step 1: Navigate to main page
        WebDriver wd = new FirefoxDriver();
        MudahMainPage.launchMainPage(wd);
        //Test Step 2: Click on Properties
        MudahMainPage.clickOnPropertiesCategory(wd);
        //Test Step 3: Verify url
        Common.sleep(2);
            //screenshot for proof
        String actualResults = wd.getCurrentUrl();
        if(actualResults.equals(expectedResults)){
            Logging.takeScreen("Pass_Scenario2");
            System.out.println("Scenario 2 passed.");
        }
        else{
            Logging.takeScreen("Fail_Scenario2");
            System.out.println("Scenario 2 failed.");
        }
        //Clean up
            //Close browser
        wd.quit();
            //Close log
        Logging.closeLog();
    }
    
}

