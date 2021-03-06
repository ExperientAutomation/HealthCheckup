package Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Parameters;

public class BrowserFactory {

	protected static ConfigReader config = new ConfigReader();

	private static Map<String, WebDriver> drivers = new HashMap<String, WebDriver>();
	public static WebDriver driver = null;
	public static XlsUtil xls;
	
	public static WebDriver getBrowser(String browserName) {			
						
		try {
			switch (browserName) {
			case "Firefox":
				driver = drivers.get("Firefox");
				if (driver == null) {
					driver = new FirefoxDriver();
					drivers.put("Firefox", driver);
				}
				break;
			case "IE":
				driver = drivers.get("IE");
				if (driver == null) {

					DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
					capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
					System.setProperty("webdriver.ie.driver", "N:\\QA\\R&DQA\\Selenium\\Drivers\\IEDriverServer.exe");
					driver = new InternetExplorerDriver(capabilities);
					drivers.put("IE", driver);

				}
				break;
			case "Chrome":
				driver = drivers.get("Chrome");
//				if (driver == null) {
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") +"//Drivers//chromedriver.exe");
					driver = new ChromeDriver();
					driver.manage().window().maximize();
					driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
					driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
					drivers.put("Chrome", driver);
//				}
				break;

			case "ChromeOptions":
				driver = drivers.get("ChromeOptions");
				if (driver == null) {
					System.setProperty("webdriver.chrome.driver",  System.getProperty("user.dir") +"//Drivers//chromedriver.exe");
					ChromeOptions cOptions = new ChromeOptions();
					cOptions.addExtensions(new File("MultiPass-for-HTTP-basic-authentication_v0.7.4.crx"));
					driver = new ChromeDriver(cOptions);
					driver.manage().window().maximize();
					drivers.put("ChromeOptions", driver);
				}
				break;
			}

		}

		catch (Exception ex) {
			ex.printStackTrace();
		}
		return driver;
	}

}