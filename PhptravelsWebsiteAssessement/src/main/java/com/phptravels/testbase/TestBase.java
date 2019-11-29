package com.phptravels.testbase;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.phptravels.constants.FilePath;
import com.phptravels.constants.TimeConstant;
import com.phptravels.helper.CommonUtility;
import com.phptravels.helper.PropertyFileLoader;
/**
 * In this class i'm loading my webdriver .
 * @author Mohit.Jaiswal
 *
 */
public class TestBase {
	/**
	 * switch case is created for the browsers-chrome,firefox,ie.
	 * 
	 * @throws IOException
	 */
	BaseClassGrid grid = new BaseClassGrid();
	public WebDriver driver;// remove it when use the extent report
	CommonUtility common = new CommonUtility();
	PropertyFileLoader properties = new PropertyFileLoader();
	public Properties promote;

	
	@BeforeTest
	public void configSetup(ITestContext context) throws IOException {
	
	String 	webDriverName=properties.getConfig("webDriverName");
		
		
		String suiteName=context.getSuite().getName();
		 String gridConfig=properties.getConfig("gridRun");
		if (gridConfig.equalsIgnoreCase("grid")) {
           
			driver = grid.getDriver(webDriverName);
		} else {

			switch (webDriverName) {
			case "chrome":
				System.setProperty("webdriver.chrome.driver", FilePath.CHROME_EXE);
				driver = new ChromeDriver();
				break;
			case "firefox":
				System.setProperty("webdriver.gecko.driver", FilePath.FIREFOX_EXE);
				driver = new FirefoxDriver();
				break;
			case "ie":
				System.setProperty("webdriver.ie.driver", FilePath.IE_EXE);
				driver = new InternetExplorerDriver();
				break;
			}

		}
		
		
	
		String url=properties.getConfig(suiteName);
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(TimeConstant.IMPLICIT_WAIT, TimeUnit.SECONDS);

	}

	@AfterTest
	public void tearDown() {
		driver.quit();// quit the webdriver
	}
}
