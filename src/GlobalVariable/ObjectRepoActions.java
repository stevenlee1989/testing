package GlobalVariable;

import ReusableComponents.Logging;

import java.io.File;

import jxl.Sheet;
import jxl.Workbook;

import java.util.HashMap;

public class ObjectRepoActions {


    
    @SuppressWarnings("rawtypes")
	private static HashMap objectRepository;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap loadingObjectRepository(String filePath){
        //parsing from XLS to dictionary as Object Repository
        //return in form of <page>_<name> as reference
        System.out.println("Trying to load Object Repo@"+filePath);
        HashMap objects = new HashMap();
        String sheetName;
        try{
            Workbook wb = Workbook.getWorkbook(new File(filePath));
            int numberOfSheets = wb.getNumberOfSheets();

            for(int k=0;k<numberOfSheets;k++){
                Sheet s = wb.getSheet(k);
                sheetName = s.getName();
                int totalRows = s.getRows();
                for(int i=1;i<totalRows;i++){
                    ReusableComponents.Common.debugMsg(sheetName+"_"+s.getCell(0,i).getContents().toString()+"::"+s.getCell(1,i).getContents().toString());
                    objects.put(sheetName+"_"+s.getCell(0,i).getContents().toString(),s.getCell(1,i).getContents().toString());
                }    
            }
            wb.close();
            objectRepository = objects;
            Logging.writeToLog("Successfully Loaded Object Library");
            return objects;
        } 
        catch(Exception e){
            Logging.writeToLog("Error Loading Object Library"+e.getMessage());
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public static String getObject(String objectPageName){
        if(objectRepository.get(objectPageName)== null||objectRepository.get(objectPageName).toString().equals("")){
            Logging.writeToLog("Unable to find object:"+objectPageName);
            return "Unable to find object:"+objectPageName;
        }
        else{
            return objectRepository.get(objectPageName).toString();
        }
    }
    
    
}
