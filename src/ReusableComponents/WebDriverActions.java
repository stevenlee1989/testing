package ReusableComponents;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 *
 * @author chong
 */
public class WebDriverActions {
    
    private static long maxWaitTime = 5000;
    
    
    public void navigateWeb(WebDriver driver, String website){ 
        driver.get(website);
    }
    
    public int closeBrowser(WebDriver driver){
        driver.quit();
        return 1;
    }
    
    public WebElement findElementByID(WebDriver driver, String elementID){
        try{
            WebElement we = (new WebDriverWait(driver, maxWaitTime))
                .until(ExpectedConditions.presenceOfElementLocated(By.id(elementID)));
            return we;
        }
         catch(Exception NoSuchElementException){
            Common.debugMsg(NoSuchElementException.getMessage());
            Logging.writeToLog("Error: Unable to find ID:"+elementID);
            return null;
        }
    }
    
    public int checkboxbyID(WebDriver driver, String id, int check){
        try{
            WebElement checkbox = driver.findElement(By.id(id));
            if(checkbox.isSelected()){
                if(check==0){
                    checkbox.click();
                }
            }
            else{
                if(check==1){
                    checkbox.click();
                }
            }
            return 1;
        }
        catch(Exception NoSuchElementException){
            Common.debugMsg(NoSuchElementException.getMessage());
            Logging.writeToLog("Error: Unable to find ID:"+id);
            return 0;
        }
    }
	
    public int setTextbyID(WebDriver driver, String id, String text){
        try{
            WebElement textBox = findElementByID(driver, id);
            textBox.clear();
            textBox.sendKeys(text);
            return 1;
        }
        catch(Exception NoSuchElementException){
            Common.debugMsg(NoSuchElementException.getMessage());
            Logging.writeToLog("Error: Unable to find ID:"+id);
            return 0;
        }
    }
    
    public int setDropdownbyID(WebDriver driver, String id, String text){
        try{
            WebElement select = findElementByID(driver, id);
            List<WebElement> options = select.findElements(By.tagName("option"));
            for (WebElement option : options) {
                Common.debugMsg("Looking for:"+text+":Found:"+option.getText());
                if(text.trim().equalsIgnoreCase(option.getText().trim())){
                    option.click();
                    break;
                }
            }
            return 1;
        }
        catch(Exception NoSuchElementException){
            Common.debugMsg(NoSuchElementException.getMessage());
            Logging.writeToLog("Error: Unable to find ID:"+id+";Text:"+text);
            return 0;
        }
     }
    
    public boolean setDropdownbyText(WebElement element, String text){
        try{
            Select select = new Select(element);
            select.selectByVisibleText(text);
            return true;
        }catch(Exception e){
            Common.debugMsg(e.getMessage());
            System.out.println("Error on SetDropdownbyText: "+text+" "+e);
            return false;
        }
    }
    
    public boolean setDropdownbyValue(WebElement element, String value){
        try{
            Select select = new Select(element);
            select.selectByValue(value);
            return true;
        }catch(Exception e){
            Common.debugMsg(e.getMessage());
            System.out.println("Error on SetDropdownbyValue: "+value+" "+e);
            return false;
        }
    }
    
    public boolean setDropdownbyIndex(WebElement element, int index){
        try{
            Select select = new Select(element);
            select.selectByIndex(index);
            return true;
        }catch(Exception e){
            Common.debugMsg(e.getMessage());
            System.out.println("Error on SetDropdownbyText: "+Integer.toString(index)+" "+e);
            return false;
        }
    }

}