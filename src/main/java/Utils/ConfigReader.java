package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Properties;

public class ConfigReader {

	Properties pro , cre;

	public ConfigReader() {

		try {
			File src = new File (System.getProperty("user.dir")+"\\Configuration\\Config.property");
			
			FileInputStream fis = new FileInputStream(src);
			pro = new Properties();
			pro.load(fis);
		} catch (Exception e) {
			System.out.println("Exception was === " + e.getMessage());
		}
	}
	
	public String LoginCredentails(String key) {
		
		String path = "N:\\QA\\R&DQA\\Selenium\\GlobalCredentials\\LoginCredentials.properties";

		FileReader fio;
		Properties objRepoProp = null;
		try {
			fio = new FileReader(path);
			objRepoProp = new Properties();
			objRepoProp.load(fio);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return objRepoProp.getProperty(key);
	}

	public String getChromePath() {

		return pro.getProperty("ChromeDriver");
	}	
	
	public String getexcelpath(){
		return pro.getProperty("excelpath");
	}
	
	public String getexcelpathRDONLY(){
		return pro.getProperty("excelpathRDONLY");
	}
	
	public String getqawebreg() {

		return pro.getProperty("qawebreg");
	}
	
	public String getqashowman() {

		return pro.getProperty("qashowman");
	}
	public String getqanewproduction() {

		return pro.getProperty("qanewproduction");
	}
	
	public String getqawebstaff(){
		return pro.getProperty("qawebstaff");
	}
	public String getqassw(){
		return pro.getProperty("qassw");
	}
	
	public String getProdreporting(){
		return pro.getProperty("prodReporting");
	}

	public String getprodwebreg() {

		return pro.getProperty("prodwebreg");
	}
	
	public String getProdshowman() {

		return pro.getProperty("prodshowman");
	}
	public String getProdnewproduction() {

		return pro.getProperty("prodnewproduction");
	}
	
	public String getProdwebstaff(){
		return pro.getProperty("prodwebstaff");
	}
	public String getProdssw(){
		return pro.getProperty("prodssw");
	}
	
	public String getqareporting(){
		return pro.getProperty("qaReporting");
	}
	
	public String getprodDP(){
		return pro.getProperty("prodDP");
	}
	
	public String getscreenshotpath(){
		return pro.getProperty("screenshotpath");
	}
	
}
