package Utils;

import java.io.File;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.google.common.io.Files;

public class ErrorCheck extends BrowserFactory {

	int usernamecolumn = 10;
	int notescolumn = 11;

	String sheetname = "EventXL App URLS";
	StringBuffer allerrors = new StringBuffer();

	// This method validates various errors on the page
	public void testErrorOnPage(WebDriver ldriver, int lcol,
			int lrow,String subenv, String mainenv, String showcode, int size)
			
		throws Exception {
		String des;
		String screenshotpath = config.getscreenshotpath();

		int column = lcol;		
		int AppNamecol = 0;
		int AppNamerow = lrow;
		String AppName = xls.getCellData(sheetname, AppNamecol, AppNamerow);

		if (subenv.equalsIgnoreCase(""))
			mainenv = "Live";
		System.out.println("Size-" + size);

		try {
			if (size <= 0) {

				/*
				 * (ldriver.getTitle().toLowerCase().contains("403") ||
				 * (ldriver.getTitle().toLowerCase().contains("error")) ||
				 * (ldriver.getCurrentUrl().toLowerCase().contains("404")) ||
				 * (ldriver.getCurrentUrl().toLowerCase().
				 * contains("The resource cannot be found")) || (size>=0) ||
				 * (ldriver.getCurrentUrl().toLowerCase().contains("error"))) {
				 */
				Thread.sleep(1000);
				
				StringBuilder scpath = new StringBuilder(screenshotpath);
				scpath.append(AppName + "-" + mainenv + "-" + subenv + "-"+ showcode + ".png");
				des = scpath.toString();

				// Script to Screenshot
				TakesScreenshot ts = (TakesScreenshot) driver;
				File source = ts.getScreenshotAs(OutputType.FILE);

				File destination = new File(des);
				Files.copy(source, destination);
				System.out.println("Screenshot taken");

				xls.setCellData(sheetname, column, lrow, "Fail");
				xls.setCellData(sheetname, usernamecolumn, lrow,
						System.getProperty("user.name"));
				allerrors.append("Error was occured in " + subenv + " "
						+ mainenv + " " + showcode + "\n");
				xls.setCellData(sheetname, notescolumn, lrow,
						allerrors.toString());
				System.out.println("Fail " +AppName + "-" + mainenv + "-" + subenv + "-"+ showcode);

				
			} else {
				Thread.sleep(1000);
				xls.setCellData(sheetname, column, lrow, "Pass");
				xls.setCellData(sheetname, usernamecolumn, lrow,
						System.getProperty("user.name"));
				System.out.println("Pass " +AppName + "-" + mainenv + "-" + subenv + "-"+ showcode);
			}

		} catch (Exception e) {
			System.out.println("Exception while taking Screenshot " + e.getMessage());
		}
	}

}