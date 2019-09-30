package CommonLibraries;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.google.common.io.Files;

public class FunctionLibraries {

	//Get the current date
		public static String fn_GetDate(){
			//Get the current system date
			String Current_Date = new SimpleDateFormat("M-d-yyyy").format(Calendar.getInstance().getTime());		
			return Current_Date;
		}
		
		//Get the time
		public static String fn_GetTime(){
			//Get the current system date
			String Current_Time = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());		
			return Current_Time;
		
		}
		
	//Create Folder with current date
		public static File fn_CreateResultFolder() throws Exception{

			//Get the date
			String current_Date = fn_GetDate();

			String Resultpath = AppConstant.RESULT_FOLDERLOCATION+current_Date;
			File file = new File(AppConstant.RESULT_FOLDERLOCATION);
			File file1 = new File(Resultpath);
			String[] Filenames = file.list();
			boolean Folderexist = false;
			
			//Verify the result folder exists 
			for(String name : Filenames)
			{
				if (name.toString().equals(current_Date.toString())) {
					Folderexist = true;
					break;
				}
			}
			
			//Create the result folder if it doesn't exists
			if (!Folderexist) {
				file1.mkdir();
				Folderexist = true;
			}
			
			return file1;
		}
		
		//Creates Scenario folder
		public static File fn_CreateFeatureFolder(File resultFoldername, String FeatureName) {
			
			String FeaturePath = resultFoldername+"\\"+FeatureName;
			File FeatureNameFolderpath = new File(FeaturePath);
			String[] FeatureNames = resultFoldername.list();
			boolean FeatureFolderexist = false;
			
			//Verify the result folder exists 
			for(String feature : FeatureNames)
			{
				if (feature.toString().equals(FeatureName)) {
				     FeatureFolderexist = true;
					break;
				}
			}
			
			//Create the folder with the fund Name
			if (!FeatureFolderexist) {
				FeatureNameFolderpath.mkdir();
			}
			return FeatureNameFolderpath;
		}
		
		//Create the test script name folder
		public static File fn_CreateTestScriptNameFolder(File featureNameFolder,String testscirptname) {
			
			String TestscriptfolderName = featureNameFolder+"\\"+testscirptname+"_"+fn_GetDate()+"_"+fn_GetTime();
			String SnapshotFoldername =  TestscriptfolderName+"\\Snapshot";
			File TestScriptFolderpath = new File(TestscriptfolderName);
			File SnapshotFolderpath = new File(SnapshotFoldername);
			
			//String[] TestScriptFolder = fundNameFolder.list();
			//boolean FundFolderexist = false;
			
			////Verify the result folder exists 
			//for(String TestFolder : TestScriptFolder)
			//{
				//if (TestFolder.toString().equals(testscirptname)) {
					//FundFolderexist = true;
					//break;
				//}
			//}
			
			//Create the folder with the test name
			//if (!FundFolderexist) {
				TestScriptFolderpath.mkdir();
				SnapshotFolderpath.mkdir();
			//}
				
			return TestScriptFolderpath;
		}
		
		//Creates the HTML file
		public static File fn_CreateHTML(File RuntimeResultFolderlocation) throws Exception {
			
			String ResFold = RuntimeResultFolderlocation+"\\ResultTemplate.html";
			System.out.println(ResFold);
			File ToHTMLfolder = new File(ResFold);
			File FromHTMLfolder = new File(AppConstant.HTML_LOCATION);
			Files.copy(FromHTMLfolder, ToHTMLfolder);	
			return ToHTMLfolder;
			
		}
		
		//Open the TR tag in the HTML
		public static void fn_Open_TR_Tag(String HTML_FilePath) throws IOException {
			
			FileWriter filewrite = new FileWriter(HTML_FilePath,true);
		    BufferedWriter BufferWrite = new BufferedWriter(filewrite);
		    PrintWriter write = new PrintWriter(BufferWrite);
		    
		    write.println("<tr>");
		    write.close();
		    BufferWrite.close();
		    filewrite.close();
		}
		
		//Close the TR tag in the HTML
		public static void fn_Close_TR_Tag(String HTML_FilePath) throws IOException {
			
			FileWriter filewrite = new FileWriter(HTML_FilePath,true);
		    BufferedWriter BufferWrite = new BufferedWriter(filewrite);
		    PrintWriter write = new PrintWriter(BufferWrite);
		    
		    write.println("</tr>");
		    write.close();
		    BufferWrite.close();
		    filewrite.close();
		}
		
		//Writes a new entry in the HTML reporter by using the existing screenshot path
		public static String fn_Update_HTML(String HTML_FilePath, String TestCase,String Status, String Step, String Description, WebDriver Driver ,String Snapshot_Path) throws IOException, UnsupportedOperationException, Throwable {
			
			//Open TR Tag
			fn_Open_TR_Tag(HTML_FilePath);
		    
			FileWriter filewrite = new FileWriter(HTML_FilePath,true);
		    BufferedWriter BufferWrite = new BufferedWriter(filewrite);
		    PrintWriter write = new PrintWriter(BufferWrite);
		    
		    String Time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		    String Snapshotvalue = null;
		    String DataToAppend = null;
		    
		    //Capture the snapshot
			Snapshotvalue = "<a href = "+Snapshot_Path+">Snap Shot</a>";
		    
		    //populate the appending line
		    if (Status.equalsIgnoreCase("PASS")) {
		    	DataToAppend = "<td>"+TestCase+"</td><td><font color=\"limegreen\">PASS</font></td><td>"+Step+"</td><td>"+Description+"</td><td>"+Time+"</td><td>"+Snapshotvalue+"</td>";
			} else if (Status.equalsIgnoreCase("FAIL")) {
				DataToAppend = "<td>"+TestCase+"</td><td><font color=\"Red\">FAIL</font></td><td>"+Step+"</td><td>"+Description+"</td><td>"+Time+"</td><td>"+Snapshotvalue+"</td>";
			} else if (Status.equalsIgnoreCase("WARN")) {
				DataToAppend = "<td>"+TestCase+"</td><td><font color=\"Yellow\">WARN</font></td><td>"+Step+"</td><td>"+Description+"</td><td>"+Time+"</td><td>"+Snapshotvalue+"</td>";
			}	

		    write.println(DataToAppend);
		    write.close();
		    BufferWrite.close();
		    filewrite.close();
			
		    //Close TR Tag
		    fn_Close_TR_Tag(HTML_FilePath);
		    
	    	return Snapshot_Path;
			
		}
		
		//Writes a new entry in the HTML reporter by taking the screenshot
		public static String fn_Update_HTML(String HTML_FilePath, String TestCase,String Status, String Step, String Description, WebDriver Driver ,boolean snapshot) throws IOException, UnsupportedOperationException, Throwable {
			
			//Open TR Tag
			fn_Open_TR_Tag(HTML_FilePath);
		    
			FileWriter filewrite = new FileWriter(HTML_FilePath,true);
		    BufferedWriter BufferWrite = new BufferedWriter(filewrite);
		    PrintWriter write = new PrintWriter(BufferWrite);
		    
		    String Time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		    String Snapshotvalue = null;
		    String DataToAppend = null;
		    String Screenshotname = "Snapshots_"+fn_GetDate()+"_"+fn_GetTime()+".PNG";
		    String Snapshotpath = HTML_FilePath.replace("ResultTemplate.html", "")+"Snapshot\\"+Screenshotname;
		    
		    //Capture the snapshot
		    if (snapshot) {
		    	//BufferedImage screencapture = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		    	//File file = new File(Snapshotpath);
		    	//Save as JPEG
		    	//ImageIO.write(screencapture,"jpg",file);
		    	
				File screen = ((TakesScreenshot)Driver).getScreenshotAs(OutputType.FILE);
				File ScreeenshotLocation = new File(Snapshotpath);
				org.apache.commons.io.FileUtils.copyFile(screen,ScreeenshotLocation);
				Snapshotvalue = "<a href = "+Snapshotpath+">Snap Shot</a>";
		    	
			} else {
				Snapshotvalue = "NA";
			}
		    
		    //populate the appending line
		    if (Status.equalsIgnoreCase("PASS")) {
		    	DataToAppend = "<td>"+TestCase+"</td><td><font color=\"limegreen\">PASS</font></td><td>"+Step+"</td><td>"+Description+"</td><td>"+Time+"</td><td>"+Snapshotvalue+"</td>";
			} else if (Status.equalsIgnoreCase("FAIL")) {
				DataToAppend = "<td>"+TestCase+"</td><td><font color=\"Red\">FAIL</font></td><td>"+Step+"</td><td>"+Description+"</td><td>"+Time+"</td><td>"+Snapshotvalue+"</td>";
			} else if (Status.equalsIgnoreCase("WARN")) {
				DataToAppend = "<td>"+TestCase+"</td><td><font color=\"Yellow\">WARN</font></td><td>"+Step+"</td><td>"+Description+"</td><td>"+Time+"</td><td>"+Snapshotvalue+"</td>";
			}	

		    write.println(DataToAppend);
		    write.close();
		    BufferWrite.close();
		    filewrite.close();
			
		    //Close TR Tag
		    fn_Close_TR_Tag(HTML_FilePath);
		    
		    //Return the snapshot path
		    if (snapshot) {
		    	return Snapshotpath;
			} else {
				return "";
			}
		}
		    
		  //End the HTML reporter by calculating the number of PASS/FAIL/WARNING
			public static void fn_End_HTML (String HTMLFilePath) {
				
				int beginIndex = 0;
				int endIndex = 0;
				String Executionstart = "EXECUTION STARTED ON </td><td>";
				int Extstart = Executionstart.trim().length();
				
				try {
					
					FileInputStream fileinput = new FileInputStream(HTMLFilePath);
					String content = IOUtils.toString(fileinput, "UTF-8");
					beginIndex = content.indexOf(Executionstart);
					endIndex = beginIndex+Extstart+8;
					
					int passcount = 0;
					int failcount = 0;
					int warncount = 0;
					
					//Calculate the number of Pass, Fail, Warnings		
					if (content.contains(">PASS<")) {
						String[] PassRepetition = content.split(">PASS<");
						passcount = PassRepetition.length;
						passcount = passcount - 1;
					} if (content.contains(">FAIL<")) {
						String[] FailRepetition = content.split(">FAIL<");
						failcount = FailRepetition.length;
						failcount = failcount - 1;
					} if (content.contains(">WARN<")) {
						String[] WarnRepetition = content.split(">WARN<");
						warncount = WarnRepetition.length;
						warncount = warncount - 1;
					}
					
					String passcnt = String.valueOf(passcount);
					String failcnt = String.valueOf(failcount);
					String warncnt = String.valueOf(warncount);
					
					//Convert the start time to the milliseconds
					String StartTime = content.substring(beginIndex+Extstart, endIndex);
					String[] SplitTime = StartTime.split(":");
					long longstarthour = Long.parseLong(SplitTime[0])*60*60*1000;
					long longstartminute = Long.parseLong(SplitTime[1])*60*1000;
					long longstartsecond = Long.parseLong(SplitTime[2])*1000;
					long StartTimemillisecond = longstarthour+longstartminute+longstartsecond;
					
					//Convert the end time to the milliseconds
					String Current_Time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
					String[] SplitCurrentime = Current_Time.split(":");
					long longendhour = Long.parseLong(SplitCurrentime[0])*60*60*1000;
					long longendminute = Long.parseLong(SplitCurrentime[1])*60*1000;
					long longendsecond = Long.parseLong(SplitCurrentime[2])*1000;
					long EndTimemillisecond = longendhour+longendminute+longendsecond;
					long timedifference = EndTimemillisecond - StartTimemillisecond;
					
					//Convert the time difference to hh:mm:ss format
					String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timedifference),TimeUnit.MILLISECONDS.toMinutes(timedifference) % TimeUnit.HOURS.toMinutes(1),TimeUnit.MILLISECONDS.toSeconds(timedifference) % TimeUnit.MINUTES.toSeconds(1));
					String[] splittimeduration = hms.split(":");
					String Timeduration = splittimeduration[0]+" hr:"+splittimeduration[1]+" min:"+splittimeduration[2]+" sec";
					
					//Update the html reporter
					content = content.replaceAll("KEY_END_TIME", Current_Time);
					content = content.replaceAll("KEY_DURATION_TIME", Timeduration);
					content = content.replaceAll("KEY_PASS", passcnt);
					content = content.replaceAll("KEY_FAIL", failcnt);
					content = content.replaceAll("KEY_WARNING", warncnt);
					
					FileOutputStream fileoutput = new FileOutputStream(HTMLFilePath);
					IOUtils.write(content,fileoutput , "UTF-8");
					fileinput.close();
					fileoutput.close();
					
					//Add the filter
					fn_addFilter(HTMLFilePath);
					
				} catch (Exception e) {
				
				}
						
			}
				
			//Start the HTML by updating the table
			public static void fn_Start_HTML(String HTMLFilePath, String test_name, String feature_name) throws IOException {
				
				String MonthNumber = new SimpleDateFormat("MMM").format(Calendar.getInstance().getTime());
				String Date = new SimpleDateFormat("d").format(Calendar.getInstance().getTime());
				String Year = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
				String Current_Time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
				String ExecutionStarte_date = Date+"/"+MonthNumber+"/"+Year;
				String ExecutionStart_Time = Current_Time;
				
				FileInputStream fileinput = new FileInputStream(HTMLFilePath);
				String content = IOUtils.toString(fileinput, "UTF-8");
				content = content.replaceAll("KEY_WORKFLOW_NAME", test_name);
				content = content.replaceAll("KEY_START_TIME", ExecutionStart_Time);
				content = content.replaceAll("KEY_EXECUTIONDATE", ExecutionStarte_date);
				content = content.replaceAll("KEY_FUND_NAME", feature_name);
				FileOutputStream fileoutput = new FileOutputStream(HTMLFilePath);
				IOUtils.write(content,fileoutput , "UTF-8");
				fileinput.close();
				fileoutput.close();
			}
			
			//Add the filter to the HTML reporter
			public static void fn_addFilter(String filePath)
			{
				try
				{
					PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
				    out.println("<script language=\"javascript\" type=\"text/javascript\">");
				    out.println("//<![CDATA[");
				    out.println("setFilterGrid(\"table1\");");
				    out.println("//]]>");
				    out.println("</script>");
				    out.close();
				}
				catch(IOException e)
				{
					
				}
			}
			
			public static void fn_batch_updateHTML(){
			ArrayList<String> Fund_Names = new ArrayList<String>();
			ArrayList<String> Script_Names = new ArrayList<String>();
			ArrayList<String> ETF_Ticker = new ArrayList<String>();
			ArrayList<String> Component_Tab = new ArrayList<String>();
			ArrayList<String> HTML_Path = new ArrayList<String>();
			ArrayList<String> Status = new ArrayList<String>();
			HashMap<String, String> ExecutionFlag = new HashMap<String, String>();
			
			Fund_Names.clear();
			Script_Names.clear();
			ETF_Ticker.clear();
			Component_Tab.clear();
			Status.clear();
			ExecutionFlag.clear();
			
			String Date_ResultFolder = null;
			String Final_ResultFolder = null;
			String HTML_ResultFile = "ResultTemplate.html";	
			
			try {
					
				String BatchReportFile = AppConstant.BatchReportTemplatePath.replaceAll("Template.xls", "_"+fn_GetDate()+".xls");
				String Final_BatchReportFile = BatchReportFile.replaceAll("Settings", "");
				File ToBatchRpoertFile = new File(Final_BatchReportFile);
				File FromBatchRpoertFile = new File(AppConstant.BatchReportTemplatePath);
				File ReporterFileCopy = new File(ToBatchRpoertFile.getPath().replaceAll("BatchExecutionReport", "BatchExecutionReport//BatchReporterCopy//"));
				Files.copy(FromBatchRpoertFile, ToBatchRpoertFile);	
				
				//Get the File Name
				AppConstant.BatchReporterExcel = ToBatchRpoertFile.getPath();
				
				//Get the Date Folder in the result location
				Date_ResultFolder = fn_GetDate();
				
				//Get the Final Result Folder
				Final_ResultFolder = AppConstant.RESULT_FOLDERLOCATION+Date_ResultFolder+"\\";
				
				//Get the Fund Names
				Fund_Names = fn_Get_Fund_Names();
				
				//Get the Script Names
				Script_Names = fn_Get_Script_Names();
				
				//Get the Execution Flag
				ExecutionFlag = fn_Get_ExecutionFlag(Fund_Names, Script_Names);
				
				//Iterate with the fund name
				for (int fnd = 0; fnd < Fund_Names.size(); fnd++) {
					
					String fnd_nme = Fund_Names.get(fnd);
					
					//Iterate with the script name
					for (int script = 0; script < Script_Names.size(); script++) {
						
						String Scrpt_nme = Script_Names.get(script);
						String Final_DataFile_Path = null;
						
						if (ExecutionFlag.get(fnd_nme+"|"+Scrpt_nme).equalsIgnoreCase("Y")) {
							
							String LatestModifiedFolder = null;
							String LatestHTMLPath = null;
							
							//Get the Latest Modified Folder
							LatestModifiedFolder = fn_LastModified_Directory(Final_ResultFolder+fnd_nme,Scrpt_nme);
							
							if (LatestModifiedFolder != null) {
								
								String TestStatus = null; 
								
								//Get the latest HTML path
								LatestHTMLPath = LatestModifiedFolder+"\\"+HTML_ResultFile;
															
								//Get the Pass/Fail Status from the HTML Reporter
								TestStatus = fn_Get_Test_Status(LatestHTMLPath);
								
								//Get the Data File Path Name
								Final_DataFile_Path = AppConstant.OuputDatalocation+fnd_nme+"\\"+Scrpt_nme+".xls";
								
								//Get the Failed Data Point Level Status
								fn_Get_Fail_DataPoint_Entry(Final_DataFile_Path,fnd_nme);
								
								ETF_Ticker.add(fnd_nme);
								Component_Tab.add(Scrpt_nme);
								HTML_Path.add(LatestHTMLPath);
								Status.add(TestStatus);
								
							}
							
						}
											
					}
					
				}
				
				//Update the reporter
				fn_Update_Reporter(ETF_Ticker, Component_Tab, Status, HTML_Path);
				
				//Copy the excel file to the another folder location
				Files.copy(ToBatchRpoertFile, ReporterFileCopy);
				
				//Run the VBS file to send the mail
				Runtime.getRuntime().exec("cscript "+Common_Functions.VBS_RESULT_FilePath);
				
			} catch (Throwable e) {
				
				e.printStackTrace();
				
			}
		  
	}
			
			//Get the fund names in ArrayList
			public static ArrayList<String> fn_Get_Fund_Names() throws Throwable {
				
				ArrayList<String> FundName = new ArrayList<String>();
						
				try {
					
					FileInputStream Fip = new FileInputStream(AppConstant.MasterInputExcel);
					HSSFWorkbook wrkbk = new HSSFWorkbook(Fip);
					HSSFSheet Sheet = wrkbk.getSheet("Script_Execution");
					
					Iterator<Row> Row = Sheet.rowIterator();
					
					Row.next();
					
					while (Row.hasNext()) {
						
						HSSFRow row = (HSSFRow) Row.next();
						
						HSSFCell cell = row.getCell(0);
						
						FundName.add(cell.getStringCellValue());
						
					}
					
					//Close the workbook
					wrkbk.close();
					Fip.close();
					
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
					
				}
				
				return FundName;
				
			}
			
			//Get the Script Names
			public static ArrayList<String> fn_Get_Script_Names() throws Throwable {
			
				ArrayList<String> ScriptName = new ArrayList<String>();
				
				try {
					
					FileInputStream Fip = new FileInputStream(AppConstant.MasterInputExcel);
					HSSFWorkbook wrkbk = new HSSFWorkbook(Fip);
					HSSFSheet Sheet = wrkbk.getSheet("Script_Execution");
					
					HSSFRow row = Sheet.getRow(0);
						
					Iterator<Cell> Cell = row.cellIterator();
					
					Cell.next();
					
					while (Cell.hasNext()) {
						
						HSSFCell cell = (HSSFCell) Cell.next();
						
						ScriptName.add(cell.getStringCellValue());
						
					}
					
					//Close the workbook
					wrkbk.close();
					Fip.close();
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				return ScriptName;
				
			}
			
			//Get the Execution Flag for the Funds and Scripts
			public static HashMap<String,String> fn_Get_ExecutionFlag(ArrayList<String> FundName, ArrayList<String> ScriptName) throws Throwable {
				
				HashMap<String, String> ExecutionFlag = new HashMap<String, String>();
				
				try {
					
					FileInputStream Fip = new FileInputStream(AppConstant.MasterInputExcel);
					HSSFWorkbook Wrkbk = new HSSFWorkbook(Fip);
					HSSFSheet Sheet = Wrkbk.getSheet("Script_Execution");
					
					for (int Rownum = 0; Rownum < FundName.size(); Rownum++) {
						
						String Fund_Name = null;
						
						//Get the Fund Name
						Fund_Name = FundName.get(Rownum);
						
						HSSFRow Row = (HSSFRow) Sheet.getRow(Rownum+1);
						
						for (int Colnum = 0; Colnum < ScriptName.size(); Colnum++) {
							
							String Script_Name = null;
							String Key = null;
							String Value = null;
							
							//Get the Script Name
							Script_Name = ScriptName.get(Colnum);
							
							HSSFCell Cell = Row.getCell(Colnum+1);
							
							Key = Fund_Name+"|"+Script_Name;
							Value = Cell.getStringCellValue().toString().trim();
							
							//Update the Hash Map
							ExecutionFlag.put(Key,Value);
							
						}
						
					}
					
					//Close the workbook
					Wrkbk.close();
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				return ExecutionFlag;
			
			}
			
			//Get the last modified folder path
			public static String fn_LastModified_Directory(String FolderPath, String TestCaseName) {
				
				ArrayList<Long> lastmodified = new ArrayList<Long>();
				HashMap<Long,String> File_LastModified = new HashMap<Long, String>();
				long LatestModified = 0;
				String FinalValue = null;
				
				try {
					
					File directory = new File(FolderPath);
					File[] subdirs = directory.listFiles(new FileFilter() {
						public boolean accept (File pathname) {
							return pathname.isDirectory();
						}
					});
					
					//Get the Last Modified date to the array list
					for(File subdir : subdirs){
						if (subdir.getPath().toString().contains(TestCaseName)) {
							File file = new File(subdir.getPath());
							lastmodified.add(file.lastModified());
							File_LastModified.put(file.lastModified(),subdir.getPath());
						}
						
					}
					
					//Sort the array List
					Collections.sort(lastmodified);
					
					//Get the last value in the array list
					if (lastmodified.size() == 0) {
						LatestModified = 0;
					}else if (lastmodified.size() == 1) {
						LatestModified = lastmodified.get(0);
					} else {
						LatestModified = lastmodified.get(((lastmodified.size()) - 1));
					}
					
					//Get the file path of the last modified folder
					FinalValue = File_LastModified.get(LatestModified);
					
				} catch (Exception e) {
				
				}
				
				return FinalValue;
				
			}	

			//Get the HTML reporter status
			public static String fn_Get_Test_Status(String HTMLFilePath) throws Exception {
				
				String content = null;
				String SplitContent = null;
				String FinalStatus = "Fail";
				
				try {
					
					FileInputStream fileinput = new FileInputStream(HTMLFilePath);
					content = IOUtils.toString(fileinput, "UTF-8");
					SplitContent = ">FAIL </font></td><td class=\"2\">";
					String[] SplitValue = content.split(SplitContent);
					
					//Find the first occurrence of the string '<'
					int position = SplitValue[1].indexOf("<", 0);
					
					try {
						
						//Get the Fail Count
						int FailCount = Integer.valueOf(SplitValue[1].substring(0, position));
						
						if (FailCount > 0) {
							FinalStatus = "Fail";
						} else if (FailCount == 0) {
							FinalStatus = "Pass";
						}
						
					} catch (Exception e) {
						
						e.printStackTrace();
						FinalStatus = "Fail";
						
					}
					

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
						
				return FinalStatus;
			}
			

		
		
}
