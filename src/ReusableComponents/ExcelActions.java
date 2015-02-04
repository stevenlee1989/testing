package ReusableComponents;
import jxl.*;
import jxl.Workbook.*;
import jxl.write.*;
import java.io.*;

/**
 *
 * @author chong
 */
public class ExcelActions {
    
    public static WritableWorkbook createNewWorkbook(String filePath){
        try{
            WritableWorkbook wb = Workbook.createWorkbook(new File(filePath));
            return wb;
        }
        catch(Exception e){
            Logging.writeToLog("Failed to create workbook.");
            return null;
        }        
    }
    
    public static WritableSheet createNewWorksheet(WritableWorkbook wb, String sheetName){
        if(!(wb==null)){
            WritableSheet ws = wb.createSheet(sheetName,0);
            return ws;        
        }
        else{
            Logging.writeToLog("Failed to create worksheet.");
            return null;
        }
    }
    
    public static void setCell(WritableSheet ws, int col, int row, String cellText){
        try{
            ws.addCell(new Label(col, row, cellText));
        }
        catch(Exception e){
            Logging.writeToLog("Failed to write to cell. Value: " + cellText + "\n" + e.getMessage());
        }        
    }
    
    public static void setCell(WritableSheet ws, int col, int row, String cellText,WritableCellFormat cf){
        try{
            ws.addCell(new Label(col, row, cellText, cf));
        }
        catch(Exception e){
            Logging.writeToLog("Failed to write to cell. Value: " + cellText + "\n" + e.getMessage());
        }        
    }
    
    public static void saveAndClose(WritableWorkbook wb){
        if(!(wb==null)){
            try{
                wb.write();
                wb.close();
                wb=null;
            }
            catch(Exception e){
                Logging.writeToLog("Unable to close workbook." + "\n" + e.getMessage());
            }
        }
        else{
            Logging.writeToLog("Workbook not found. Unable to close.");
        }
    }
    
    public static String[][] loadData(String filePath){
        if(filePath.substring(filePath.lastIndexOf(".")).equals(".xls")||filePath.substring(filePath.lastIndexOf(".")).equals(".xlsx")){
            return readSingleSheetXLS(filePath);
        }
        else{
            return readCSV(filePath);
        }
    }
    
    public static String[][] readCSV(String filePath){
        try {
            FileInputStream fstream = new FileInputStream(filePath); 
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String temp;
            int numOfLines = 0;

            while ((temp = br.readLine()) != null) {
                numOfLines++;
            }
            
            
            br.close();
            in.close();
            fstream.close();
            fstream = null;
            in=null;
            br=null;
            
            fstream = new FileInputStream(filePath); 
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            
            int cellNum=0;
            int currentLineCount=0;
            String [] tempCells;
            boolean lastCellReached;
            String [][] mainTable = new String[numOfLines][];
            char specialIdentifier = (char)31;
            
            while ((temp = br.readLine()) != null) {
                if(temp.length()>1&&temp.charAt(temp.length()-1)==','){
                    temp = temp.substring(0,temp.length()-1);
                }
                if(!temp.contains("\"")){
                    //solve for no , and no "
                    mainTable[currentLineCount] =temp.split(",");
                }
                else{
                    tempCells = new String[temp.split(",").length];//temporary size, wont be larger than this, reduce it later
                    //solve for , but no " in cells
                    cellNum=0;
                    lastCellReached = false;
                    while(temp.indexOf(",")!=-1){
                        if(temp.substring(0,1).equals("\"")){
                            //if first is " then look for the ending ",
                            temp=temp.substring(1);
                            temp=temp.replaceAll("\"\"", ""+specialIdentifier);
                            if(temp.contains("\",")){
                                tempCells[cellNum]=temp.substring(0,temp.indexOf("\",")).replaceAll(""+specialIdentifier, "\"");
                                temp=temp.substring(temp.indexOf("\",")+2);
                            }
                            else{
                                tempCells[cellNum]=(temp.substring(0,temp.length()-1)).replaceAll(""+specialIdentifier, "\"");
                                lastCellReached=true;
                                //System.out.println("last cell reached.");
                                break;
                            }
                            temp=temp.replaceAll(""+specialIdentifier, "\"\"");
                            //System.out.println("1"+temp);
                        }
                        else{
                            //normal cell!
                            tempCells[cellNum] = temp.substring(0,temp.indexOf(",")).replaceAll("\"\"", "\"");
                            temp=temp.substring(temp.indexOf(",")+1);
                            //System.out.println(temp);
                        }
                        cellNum++;
                    }
                    //last cell to settle
                    if(!lastCellReached){
                        if(temp!=null||!temp.equals("")){
                            if(temp.substring(0,1).equals("\"")){                                
                                temp=temp.substring(1);
                                temp=temp.replaceAll("\"\"", ""+specialIdentifier);
                                temp=temp.replaceAll("\"", "");
                                tempCells[cellNum]=temp.replaceAll(""+specialIdentifier,"\"");
                            }
                            else{
                                
                            }
                        }
                        else{
                            tempCells[cellNum]="";
                        }  
                    }
                    //reduce
                    String [] tempRow=new String[cellNum+1];
                    for(int i=0;i<tempRow.length;i++){
                        tempRow[i]=tempCells[i];
                    }
                    mainTable[currentLineCount]=tempRow;
                }
                currentLineCount++;
            }
            
            br.close();
            in.close();
            fstream.close();
            
            return mainTable;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Logging.writeToLog("Error: " + e.getMessage());
            return null;
        }
    }
    
    public static String[][] readSingleSheetXLS(String filePath){
        try{
            Workbook wb = Workbook.getWorkbook(new File(filePath));
            Sheet s = wb.getSheet(0);
            int totalRows = s.getRows();
            String[][] output = new String[totalRows][];
            Cell[] tempCells;
            String[] tempStrs;
            for(int i=0;i<totalRows;i++){
                tempCells = s.getRow(i);
                tempStrs = new String[tempCells.length];
                for(int j=0;j<tempCells.length;j++){
                    if(tempCells[j].getContents()==null){
                        tempStrs[j] = "";
                    }
                    else{
                        tempStrs[j] = tempCells[j].getContents();
                    }
                }
                output[i] = tempStrs;
            }    
            wb.close();
            return output;
        } 
        catch(Exception e){
            System.out.println(e.getMessage());
            Common.debugMsg("Unable to read Excel file." + "\n" + e.getMessage());
            return null;
        }
    }
    
    public static void writeXLS(String fileName, String sheetName, String [][]output){
        if(fileName.contains(".")){
            if(!(fileName.substring(fileName.lastIndexOf(".")+1).equals("xls"))){
                fileName = fileName+".xls";
            }            
        }
        else{
                fileName = fileName+".xls";
        }
        WritableWorkbook wb = createNewWorkbook(ReusableComponents.Logging.getLogFolder()+Common.getSysSeparator()+fileName);
        WritableSheet ws = createNewWorksheet(wb, sheetName);
        WritableCellFormat none = new WritableCellFormat();
        try{
            none.setWrap(true);
        }
        catch(Exception e){
            Logging.writeToLog("Unable to set cell format." + "\n" + e.getMessage());
        }
        int rowNum = 0;
        for(int i=0; i<output.length;i++){
            for(int j=0;j<output[i].length;j++){
                setCell(ws, j, rowNum, output[i][j]); 
                ws.getWritableCell(j,rowNum).setCellFormat(none);
            }
            rowNum++;
        }
        saveAndClose(wb);
    }
    
    public static void writeCSV(String fileName, String[][] output){
        if(fileName.contains(".")){
            if(!(fileName.substring(fileName.lastIndexOf(".")+1).equals("csv"))){
                fileName = fileName+".csv";
            }            
        }
        else{
                fileName = fileName+".csv";
        }
        char nextLine = 10;
        try{
        // Create file 
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);
            for(int i=0; i<output.length;i++){
                for(int j=0;j<output[i].length;j++){
                    //formatting
                    if(output[i][j].contains("\"")||output[i][j].contains(",")||output[i][j].contains(""+nextLine)){
                        output[i][j] = "\""+output[i][j].replaceAll("\"", "\"\"")+"\"";
                    }
                    
                    if(j==0){
                        out.write(output[i][j]);
                    }
                    else{
                        out.write(","+output[i][j]);
                    }
                }
                out.write("\n");
            }
        //Close the output stream
            out.close();
        }catch (Exception e){//Catch exception if any
            Logging.writeToLog("Error writing CSV file:"+fileName + "\n" + e.getMessage());
        }
    }
}

