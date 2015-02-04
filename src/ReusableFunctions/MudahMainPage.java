package ReusableFunctions;

import org.openqa.selenium.WebDriver;
import ReusableComponents.*;
import GlobalVariable.ObjectRepoActions.*;

public class MudahMainPage {
    
    private static WebDriverActions wda = new WebDriverActions();
    
    public static void launchMainPage(WebDriver wd){
        wda.navigateWeb(wd, "http://www.mudah.my");
        wd.manage().window().maximize();
    }
    
    public static void clickOnCarsCategory(WebDriver wd){
        wda.findElementByID(wd, GlobalVariable.ObjectRepoActions.getObject("MudahMainPage_Cars")).click();
    }
    
    public static void clickOnPropertiesCategory(WebDriver wd){
        wda.findElementByID(wd, GlobalVariable.ObjectRepoActions.getObject("MudahMainPage_Property")).click();
    }
    
}
