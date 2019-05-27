package LanuchApplication;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utils.BrowserFactory;
import Utils.ErrorCheck;
import Utils.smlogin;

public class EventXL_Apps extends BrowserFactory {

	int showcodecolumn = 0;
	public WebDriverWait wait;	
	String sheetname = "EventXL App URLS";	
	ErrorCheck validation = new ErrorCheck();
	
	// WebReg page validation
	public void WebReg(String subenv, String mainenv, int icol, int irow) throws Exception {	
		
		try {
			
			String weburl;
		    driver = BrowserFactory.getBrowser("Chrome");
			weburl = config.getqawebreg();
			
			showcodecolumn = icol-1; 
			int Showcoderownumber = irow;
						
			if (subenv.equalsIgnoreCase("Prod")) {// Checking whether Prod or Stage
			weburl = config.getprodwebreg();
			showcodecolumn = icol-2 ;}
								
		    String showcode = xls.getCellData(sheetname,showcodecolumn,Showcoderownumber);
		
			StringBuffer newurl = new StringBuffer(weburl);
			newurl.insert(8, mainenv).append(showcode);
			driver.get(newurl.toString());
			System.out.println("New URL:"+newurl);
			driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			
			Thread.sleep(2000);
			if (driver.findElements(By.xpath("//div[@class='row fixedwidth']")).size()>0) {
				
				driver.findElement(By.xpath("(//*[contains(.,'Attendee')])[8]")).click();
				Thread.sleep(2000);
				int webreg = driver.findElements(By.xpath("//button[@ng-click='PersonSwitcher.signIn()']")).size();				
				validation.testErrorOnPage(driver, icol, irow, subenv, mainenv, showcode, webreg);
				
			} else { 
				
				validation.testErrorOnPage(driver, icol, irow, subenv, mainenv, showcode, 0);				
			}
								
			driver.close();
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
				
	}

	//  Show Manager initial page validation	
	public void ShowManlive(String subenv, String mainenv, int icol, int irow) throws Exception {

		try {
			driver = BrowserFactory.getBrowser("Chrome");
			wait = new WebDriverWait(driver, 100);
			String weburl = config.getqashowman();
			
			showcodecolumn = icol-1;	
			int Showcoderownumber = irow;
			if (subenv.equalsIgnoreCase("Prod")) { // Checking whether Prod or Stage
			weburl = config.getProdshowman();
			showcodecolumn = icol-2 ;
			}
			
			String showcode = xls.getCellData(sheetname,showcodecolumn,Showcoderownumber);
						
			StringBuffer newurl = new StringBuffer(weburl);
			newurl.insert(8,mainenv);			
			driver.get(newurl.toString());
			System.out.println("New URL:"+newurl.toString());
						
			if (driver.findElements(By.xpath("//input[contains(@id,'Username') or contains(@id,'UserName')]")).size() > 0) {
					
				smlogin.externallogin(driver);					
				if (driver.findElements(By.xpath("//input[@class='inputShowCode']")).size()>0) {
				driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(showcode);
				driver.findElement(By.xpath("//input[@value='Go!']")).click();
				} 
				int showmanshowlevel = driver.findElements(By.xpath("//td[contains(.,'Show Admin')]")).size();
				validation.testErrorOnPage(driver, icol, irow, subenv, mainenv, showcode, showmanshowlevel);
//				System.out.println("QA Show Manager Opened");
				
			} else {
				
				validation.testErrorOnPage(driver, icol, irow, subenv, mainenv, showcode, 0);
			}			
			driver.close();
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
	// New Production View Page validation
	public void NewProductionlive(String subenv, String mainenv, int icol, int irow) throws Exception {

		try {
			
			driver = BrowserFactory.getBrowser("Chrome");
			wait = new WebDriverWait(driver, 120);
			String newprodnurl = config.getqanewproduction();
			
			showcodecolumn = icol-1;
			int Showcoderownumber = irow;
			if (subenv.equalsIgnoreCase("Prod")) {// Checking whether Prod or Stage
			newprodnurl = config.getProdnewproduction();
			showcodecolumn = icol-2 ;}
			 String showcode = xls.getCellData(sheetname,showcodecolumn,Showcoderownumber);
			
			StringBuffer newurl = new StringBuffer(newprodnurl);
			newurl.insert(8,mainenv);
			driver.get(newurl.toString());		
			System.out.println("New URL:"+newurl.toString());
			if (driver.findElements(By.xpath("//input[contains(@id,'Username') or contains(@id,'UserName')]")).size() > 0)
				smlogin.externallogin(driver);
			
		    if (driver.findElements(By.xpath("//input[@class='inputShowCode']")).size()>0) {
				
				driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(showcode);
				driver.findElement(By.xpath("//input[@value='Go!']")).click();
				// Go to Production Request tab
				driver.findElement(By.xpath("//*[contains(text(),'Production')]")).click();
				Thread.sleep(5000);
				driver.findElement(By.xpath("//*[contains(text(),'Production')]")).click();
				Thread.sleep(5000);
				driver.switchTo().frame(0);
				int newprod = driver.findElements(By.xpath("//button[contains(.,'Add New Production Request')]")).size();
//			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,'Add New Production Request')]")));
//				System.out.println("QA New Production Opened");
				validation.testErrorOnPage(driver, icol, irow, subenv, mainenv, showcode, newprod);
				
			}else {
				
				validation.testErrorOnPage(driver, icol, irow, subenv, mainenv, showcode, 0);
			}
		    driver.close();
//		String showcode = xls.getCellData(sheetname, 7, showcodecolumn);
//		System.out.println("ShowCode: " + showcode);			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	// SSW page validation
	public void SSW(String subenv,String mainenv, int icol, int irow) throws Exception {

		try {
//			showcodecolumn = 6;		
			driver = BrowserFactory.getBrowser("Chrome");
			wait = new WebDriverWait(driver, 120);
			String qassw =config.getqassw();
			
			showcodecolumn = icol-1;
			int Showcoderownumber = irow;
			
			 String showcode = xls.getCellData(sheetname,showcodecolumn,Showcoderownumber);			
			
			StringBuffer newurl = new StringBuffer(qassw);
			newurl.insert(8,mainenv);			
			driver.get(newurl.toString());
			System.out.println("New URL:"+newurl.toString());
			driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
			
			if (driver.findElements(By.xpath("//input[contains(@id,'Username') or contains(@id,'UserName')]")).size() > 0)
				smlogin.smloginChandra(driver);	
			Thread.sleep(2000);
			
			if (driver.findElements(By.xpath("(//select[@id='DropDownShowCode']/following::input)[1]")).size()>0) {
			
			// smlogin.smloginChandra(driver);
//			String showcode = xls.getCellData(sheetname, 7, showcodecolumn);

			driver.findElement(By.xpath("(//select[@id='DropDownShowCode']/following::input)[1]")).sendKeys(showcode, Keys.ENTER);
//			driver.findElement(By.xpath("(//select[@id='DropDownShowCode']/following::input)[2]")).click();
			Thread.sleep(2000);
			int ssw = driver.findElements(By.id("LabelMainContentHeader")).size();
//		    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='ButtonConfigureWidgets']")));
			validation.testErrorOnPage(driver, icol, irow, subenv, mainenv, showcode,ssw);
			
		} else {
			
			validation.testErrorOnPage(driver, icol, irow, subenv, mainenv, showcode, 0);
		}
			driver.close();	
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	// WebStaff page validation	
	public void WEbStaff(String subenv,String mainenv, int icol, int irow) throws Exception {
		
		try {
			
			driver = BrowserFactory.getBrowser("Chrome");
			wait = new WebDriverWait(driver, 100);
			driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
			String webstaff = config.getqawebstaff();
			
			showcodecolumn = icol-1;
			int Showcoderownumber = irow;
			if (subenv.equalsIgnoreCase("Prod")) {// Checking whether Prod or Stage
			webstaff = config.getProdwebstaff();
			showcodecolumn = icol-2 ;}
			 String showcode = xls.getCellData(sheetname,showcodecolumn,Showcoderownumber);			
		
			StringBuffer newurl1 = new StringBuffer(webstaff);
			newurl1.insert(8, mainenv).append(showcode+"/WebStaff#/login");
			driver.get(newurl1.toString());		
			System.out.println(newurl1);
			if (driver.findElements(By.xpath("//input[contains(@id,'Username') or contains(@id,'UserName')]")).size() > 0)
				smlogin.externallogin(driver);	
			
			if (driver.findElements(By.xpath("//input[@id='qcGroupNumber']")).size()>0) {
				
				driver.findElement(By.xpath("//button[contains(.,'Please Select')]")).click();

				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[1]/button")));
				driver.findElement(By.xpath("//li[1]/button")).click();
				driver.findElement(By.xpath("//button[text()='Continue']")).click();

				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Login']")));
				Thread.sleep(2000);
				driver.findElement(By.xpath("//button[text()='Login']")).click();					 
				
				int webstaffsize = driver.findElements(By.xpath("//button[@type='submit']")).size();			
				validation.testErrorOnPage(driver, icol, irow, subenv, mainenv, showcode, webstaffsize);			
							
		    	}
			
			else {
			
			validation.testErrorOnPage(driver, icol, irow, subenv, mainenv, showcode, 0);
				
			}
			driver.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	// Reporting Site page validation	
	public void Reporting( String subenv,String mainenv, int icol, int irow) throws Exception {		
		
		try {
			driver = BrowserFactory.getBrowser("Chrome");
			
			wait = new WebDriverWait(driver, 100);
			String qareport = config.getqareporting();
			
			showcodecolumn = icol-1;
			int Showcoderownumber = irow;
			if (subenv.equalsIgnoreCase("Prod")) {// Checking whether Prod or Stage
			qareport = config.getProdreporting();
			showcodecolumn = icol-2 ;}
			String showcode = xls.getCellData(sheetname,showcodecolumn,Showcoderownumber);		
						
			StringBuffer newurl = new StringBuffer(qareport);
			newurl.insert(8,mainenv);
			driver.get(newurl.toString());		
			driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
			System.out.println("New URL:"+newurl.toString());
			if (driver.findElements(By.xpath("//input[contains(@id,'Username') or contains(@id,'UserName')]")).size() > 0)
				smlogin.externallogin(driver);	
			
			Thread.sleep(3000);
			driver.switchTo().frame("menu");
			
			if (driver.findElements(By.xpath("//select[@id='DropDownListShow']")).size()>0) {
					
//			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@id='DropDownListShow']")));
			Select dropdown = new Select(driver.findElement(By.xpath("//select[@id='DropDownListShow']")));
			dropdown.selectByValue(showcode);
			driver.switchTo().defaultContent();
			driver.switchTo().frame("main");
			int report = driver.findElements(By.xpath("//h1[contains(.,'Quick Links')]")).size();
			
//			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(.,'Quick Links')]")));
			
//			System.out.println("QA Reporting Site Opened");
			validation.testErrorOnPage(driver, icol, irow, subenv, mainenv, showcode, report);			
			
			} else {				
				validation.testErrorOnPage(driver, icol, irow, subenv, mainenv, showcode, 0);
			}
			
		} catch (Exception e) {		
			e.printStackTrace();
		}
		
		driver.close();
	}
	
	//	DP Site initial page validation
	public void prodDP(String subenv, String mainenv, int icol, int irow) throws Exception{
		
		driver = BrowserFactory.getBrowser("Chrome");
		driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
		String dpurl = config.getprodDP();
		StringBuffer newurl = new StringBuffer(dpurl);
		
		showcodecolumn = icol-2;
		int Showcoderownumber = irow;	
		String showcode = xls.getCellData(sheetname,showcodecolumn,Showcoderownumber);
		
		newurl.insert(8, mainenv).append(showcode+"/Dashboard/Overview");
		
		driver.get(newurl.toString());
		System.out.println("New Url: "+newurl.toString());
		if (driver.findElements(By.xpath("//input[contains(@id,'Username') or contains(@id,'UserName')]")).size() > 0)
			smlogin.externallogin(driver);		
		
		int dp = driver.findElements(By.xpath("//a[@class='link-logout']")).size();		
		validation.testErrorOnPage(driver, icol, irow, subenv, mainenv, showcode, dp);
//		System.out.println("DP Application opened");
		driver.close();
	}	

	
}
