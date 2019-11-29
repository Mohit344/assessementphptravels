package com.phptravels.testbase;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.phptravels.helper.PropertyFileLoader;
/**
 * This  consist of remotewebdriver and grid connection.
 * @author Mohit.Jaiswal
 *
 */
public class BaseClassGrid {
	/**
	 * RemoteWebDriver getDriver function is use to upload the browser.
	 * 
	 * @param browser
	 * @return
	 * @throws MalformedURLException
	 */
	PropertyFileLoader properties = new PropertyFileLoader();
	public RemoteWebDriver getDriver(String browser) throws MalformedURLException {
		// String systemip="http://55.55.52.215:2000/wd/hub";

		//return new RemoteWebDriver(new URL(FileConstant.SYSTEM_IP), getBrowserCapabilities(browser));
		String systemIpConfig=properties.getConfig("systemIp");
		return new RemoteWebDriver(new URL(systemIpConfig), getBrowserCapabilities(browser));
	}

	private static DesiredCapabilities getBrowserCapabilities(String browserType) {
		switch (browserType) {
		case "firefox":
			System.out.println(" Ready  in firefox driver");
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(CapabilityType.BROWSER_NAME, "firefox");
			return cap;
		case "chrome":
			System.out.println("ready in chrome ");
			DesiredCapabilities capC = new DesiredCapabilities();
			capC.setCapability(CapabilityType.BROWSER_NAME, "chrome");
			return capC;

		case "ie":
			System.out.println("ready in ie ");
			DesiredCapabilities capie = new DesiredCapabilities();
			capie.setCapability(CapabilityType.BROWSER_NAME, "internet explorer");
			return capie;

		default:
			System.out.println("browser : " + browserType + " is invalid, Launching Firefox as browser of choice..");
			DesiredCapabilities capfirefox = new DesiredCapabilities();
			capfirefox.setCapability(CapabilityType.BROWSER_NAME, "chrome");
			return capfirefox;

		}

	}
}
