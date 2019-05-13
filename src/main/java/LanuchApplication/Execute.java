package LanuchApplication;

import java.io.File;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Utils.XlsUtil;

public class Execute extends EventXL_Apps {
	
	String sheetname = "EventXL App URLS";	
	
	// Clear Excel sheet value
	@BeforeClass
	public void clearExcelSheet() {
		
		xls = new XlsUtil(config.getexcelpath());
		
		System.out.println("Started clearing Excel sheet value.");
		
		try {
			int j =0, i =0;
			String result = null;
			for (j = 2; j <= 9; j++) {

				for (i = 3; i <= xls.getRowCount(sheetname); i++) {
					
					xls.setCellData(sheetname, j+8, i, "");	
					result = xls.getCellData(sheetname, j, i);					
					if ((result.equalsIgnoreCase("Fail")) || (result.equalsIgnoreCase("Pass"))) {		
												
						xls.setCellData(sheetname, j, i, "");					
					}
				}
			}	
		
		System.out.println("Excel sheet value cleared");	
		
//		Delete Screenshot files		
		File dir = new File(config.getscreenshotpath());
		File[] listFiles = dir.listFiles();
		for(File file : listFiles){
			System.out.println("Deleting "+file.getName());
			file.delete(); 
		}
		
	}
		catch (Exception e) {		
		e.printStackTrace();
	}		
	
	System.out.println("Deleted Screenshot files");

	}
	
	// QA Applications
	@Test(priority = 0, retryAnalyzer = Utils.RetryListener.class, enabled = true)
	public void StageExecution() throws Exception {

		int i = 0;
		int qaevncol = 2;
		int subenvrow = 1;
		int envrow = 2;
		int resutlrow = 2;

		EventXL_Apps stageapps = new EventXL_Apps();

		for (i = 1; i <= 9; i += 3) {

			String  mainenv= xls.getCellData(sheetname, i, subenvrow);
			String subenv = xls.getCellData(sheetname, qaevncol, envrow);
			qaevncol = i + 1;
			
			System.out.println("***********************************************");
			System.out.println("Main Env: " + mainenv + " Sub Environment:" + subenv);
			System.out.println("***********************************************");

			if (mainenv.equalsIgnoreCase("Live"))
				mainenv = "";

//			stageapps.WebReg(subenv, mainenv, qaevncol, resutlrow + 1);			
//			stageapps.ShowManlive(subenv, mainenv, qaevncol, resutlrow + 2);
//			stageapps.NewProductionlive(subenv, mainenv, qaevncol, resutlrow + 3);
//			stageapps.SSW(subenv, mainenv, qaevncol, resutlrow + 4);
//			stageapps.WEbStaff(subenv, mainenv, qaevncol, resutlrow + 5);
			stageapps.Reporting(subenv, mainenv, qaevncol, resutlrow + 6);
		}
	}

	// Prod Applications
	@Test(priority = 1, retryAnalyzer = Utils.RetryListener.class, enabled = true)
	public void ProdExecution() throws Exception {

		int j = 0;
		int prodevncol = 3;
		int resutlrow = 2;

		EventXL_Apps prodapps = new EventXL_Apps();

		for (j = 1; j <= 9; j += 3) {

			// String showcode = xls.getCellData(sheetname, j, 3);
			String mainenv = xls.getCellData(sheetname, j, 1);
			String subenv = xls.getCellData(sheetname, prodevncol, 2);
						 
			System.out.println("\n***********************************************");
			System.out.println("Main Environment:" + mainenv + " Sub Environment:" + subenv);
			System.out.println("***********************************************\n");
			
			prodevncol = j + 2;

			if (mainenv.equalsIgnoreCase("Live"))
				mainenv = "";

			prodapps.WebReg(subenv, mainenv, prodevncol, resutlrow + 1);
			prodapps.ShowManlive(subenv, mainenv, prodevncol, resutlrow + 2);
			prodapps.NewProductionlive(subenv, mainenv, prodevncol, resutlrow + 3);
			prodapps.WEbStaff(subenv, mainenv, prodevncol, resutlrow + 5);
			prodapps.Reporting(subenv, mainenv, prodevncol, resutlrow + 6);
			prodapps.prodDP(subenv, mainenv, prodevncol, resutlrow + 7);

		}
	}

	// Send Status report.
	@AfterClass(enabled = true)
	public void sendEmailReport() {

		Utils.emailReport rept = new Utils.emailReport();
		rept.sendEmail(this.getClass().getSimpleName(), sheetname);
	}
}
