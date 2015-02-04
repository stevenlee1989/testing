package ReusableComponents;

public class Common {
    
    private static String system_separator = "";
    
    public static void sleep(int seconds){
        try{
            Thread.sleep(seconds*1000);
        }
        catch(Exception e){
            Logging.writeToLog("Exception error while sleeping.");
        }
    }
    
    public static void debugMsg(String in){
        /*switched off
                System.out.println(in);*/
    }
    
    public static void initSystem(){
        if(System.getProperty("os.name").startsWith("Windows")){
            system_separator = "\\";
        }
        else{
            system_separator = "/";
        }
    }
    
    public static String getSysSeparator(){
        return system_separator;
    }
}

