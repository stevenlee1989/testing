package ReusableComponents;
import java.awt.Robot;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.*;
import java.text.*;
/**
 *
 * @author chong
 */
public class Logging {
    
    private static BufferedWriter log; 
    private static boolean logOn;
    private static String logFolder = "";
    private static String currentRunName = "";
    private static String logFolderPath;
    
    public static String logInit(String folderPath){
        Date thisRun = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmm");
        currentRunName = "Run_"+df.format(thisRun);
        logFolderPath = folderPath+Common.getSysSeparator() +currentRunName;
        File directory = new File(logFolderPath+Common.getSysSeparator()+"Screenshots" +Common.getSysSeparator());
        directory.mkdirs();
        setLogon(false);
        try{
            FileWriter fstream = new FileWriter(logFolderPath+Common.getSysSeparator()+"log.txt");
            log = new BufferedWriter(fstream);
            setLogon(true);
        }catch (Exception e){
            System.out.println("Error creating log file:"+logFolderPath+Common.getSysSeparator()+"log.txt" + "\n" + e.getMessage());
        }
        return logFolderPath;
    }
    
    public static void takeScreen(String comments){
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("ddHHmmssSSS");
        try{
            BufferedImage screencapture = new Robot().createScreenCapture(
            new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()) );

            // Save as JPEG
            File file = new File(getLogFolder()+Common.getSysSeparator()+"Screenshots" +Common.getSysSeparator()+df.format(now)+"_"+comments+".jpg");
            ImageIO.write(screencapture, "jpg", file);
        }
        catch(Exception e){
            writeToLog("Error taking screenshot:"+getLogFolder()+Common.getSysSeparator()+"Screenshots" +Common.getSysSeparator()+df.format(now)+"_"+comments+".jpg" + "\n" + e.getMessage());
        }
    }
    
    public static void writeToLog(String str){
        if(logOn){
            Common.debugMsg("Writing to log:"+str);
            try{
                log.write(str+"\n");
            }
            catch(Exception e){
                System.out.println("Error writing log file" + "\n" + e.getMessage());
            }
        }
    }
    
    public static void closeLog(){
        if(logOn){
            try{
                log.close();
                log=null;
            }
            catch(Exception e){
                System.out.println("Error closing log file" + "\n" + e.getMessage());
            }
        }
    }
    
    public static void setLogFolder(String input){
        logFolder = ReusableComponents.Logging.logInit(input);
    }
    
    public static String getLogFolder(){
        return logFolder;
    }
    
    public static void setLogon(boolean in){
        logOn = in;
    }   
    
}
