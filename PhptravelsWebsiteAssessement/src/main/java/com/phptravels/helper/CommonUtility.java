package com.phptravels.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.phptravels.constants.TimeConstant;

public class CommonUtility {
	/**
	 * this CommonUtility class which conatin all reusable methods of click,sendkey,selectdropdown,scrolling
	 */
	WebElement element;

	/**
	 * method name -locatorsType
	 * this method is to select the locator like css,xpath,id,name
	 * @param locator
	 * @return
	 */
	public WebElement locatorsType(String locator, WebDriver driver) {
		String arraylocator[] = locator.split(":", 2);

		switch (arraylocator[0]) {
		// Find by xpath
		case "ID":
			System.out.println(arraylocator[1]);
			element = driver.findElement(By.id(arraylocator[1]));

			break;
		case "XPATH":

			element = driver.findElement(By.xpath(arraylocator[1]));

			break;
		// Find by css
		case "CSS":
			element = driver.findElement(By.cssSelector(arraylocator[1]));
			break;
		// find by class
		case "CLASSNAME":
			element = driver.findElement(By.className(arraylocator[1]));
			break;

		// find by name
		case "NAME":
			element = driver.findElement(By.name(arraylocator[1]));
			break;

		// find by link
		case "LINK":
			element = driver.findElement(By.linkText(arraylocator[1]));
			break;

		// find by partial link
		case "PARTIALLINK":
			element = driver.findElement(By.partialLinkText(arraylocator[1]));
			break;
		// find by tagName
		case "TAGNAME":
			element = driver.findElement(By.tagName(arraylocator[1]));
			break;

		}
		return element;
	}

	/**
	 * method click the Element using the fluent wait concepts ignoring the
	 * ElementClickInterceptedException.
	 * 
	 * @param driver
	 * @param xpath
	 */

	public void clickElement(WebDriver driver, final String locator) {
		FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
				.ignoring(ElementClickInterceptedException.class)
				.pollingEvery(Duration.ofMillis(TimeConstant.POLLING_TIME))
				.withTimeout(Duration.ofMillis(TimeConstant.TIME));
		fluentWait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				WebElement element = locatorsType(locator, driver);
				element.isDisplayed();
				element.click();
				return true;
			}

		});

	}

	/**
	 * 
	 * this method will text and select the items from the drop down.
	 * 
	 * @param driver
	 * @param xpath
	 * @param timeOut
	 * @param text
	 */

	public void selectDropdown(WebDriver driver, final String locator, int timeOut, final String text) {
		FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
				.pollingEvery(Duration.ofMillis(TimeConstant.POLLING_TIME))
				.withTimeout(Duration.ofMillis(TimeConstant.TIME));
		fluentWait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {

				WebElement element = locatorsType(locator, driver);
				Select selectElement = new Select(element);
				selectElement.selectByVisibleText(text);
				return true;
			}

		});
	}

	/**
	 * method click the blank fields and send the text to be entered
	 * 
	 * @param driver
	 * @param xpath
	 * @param timeOutInSeconds
	 * @param text
	 */
	public void clickAndSendText(WebDriver driver, final String locator, int timeOutInSeconds, final String text) {
		FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
				.ignoring(ElementClickInterceptedException.class)
				.pollingEvery(Duration.ofMillis(TimeConstant.POLLING_TIME))
				.withTimeout(Duration.ofMillis(TimeConstant.TIME));

		fluentWait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				locatorsType(locator, driver);
				element.sendKeys(text);
				element.isDisplayed();
				element.click();
				return true;
			}
		});
	}

	/**
	 * method takes parameters as
	 * 
	 * @param driver
	 * @param xpath
	 * @param timeOutInSeconds
	 * @param text             and get the text of the web element
	 */
	public String getText(WebDriver driver, final String locator) {
		FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
				.ignoring(ElementClickInterceptedException.class)
				.pollingEvery(Duration.ofMillis(TimeConstant.POLLING_TIME))
				.withTimeout(Duration.ofMillis(TimeConstant.TIME));
		return fluentWait.until(new Function<WebDriver, String>() {
			public String apply(WebDriver driver) {

				locatorsType(locator, driver);
				element.isDisplayed();
				String text = element.getText();

				return text;
			}

		});
	}

	/**
	 *  this method get elements
	 * 
	 * @param driver
	 * @param xpath
	 * @param text
	 * @return WebElement
	 */
	public WebElement getElement(WebDriver driver, String locator, String text) {
		locatorsType(locator, driver);
		element.sendKeys(text);
		return element;
	}

	/**
	 * method takes parameters as
	 * 
	 * @param driver
	 * @param XPathIsDisplayed
	 * @return a boolean value for the displayed element on the web page
	 */
	public boolean isDisplayed(WebDriver driver, String locator) {
		boolean isDisplayed = false;
		try {
			locatorsType(locator, driver);
			element.isDisplayed();
		} catch (Exception e) {
			isDisplayed = false;
		}
		return isDisplayed;
	}
/**
 * this  method clear the text present in the input box
 * @param driver
 * @param locator
 */
	public void clearElement(WebDriver driver, final String locator) {
		FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
				.ignoring(ElementClickInterceptedException.class)
				.pollingEvery(Duration.ofMillis(TimeConstant.POLLING_TIME))
				.withTimeout(Duration.ofMillis(TimeConstant.TIME));
		fluentWait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				locatorsType(locator, driver);
				element.isDisplayed();
				element.clear();
				return true;
			}
		});
	}

	/**
	 * method takes parameters as
	 * 
	 * @param driver
	 * @param xpath  and uses the explicit wait concept
	 * @return
	 * @return a boolean value after checking the visibility of the.
	 */
	public boolean isElementVisible(WebDriver driver, String locator) {

		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, TimeConstant.EXPLICIT_WAIT);
		try {
			wait.until(ExpectedConditions.visibilityOf(locatorsType(locator, driver)));
		} catch (Exception exception) {

			return false;
		}
		return true;
	}

	/**
	 * method name-getElementsList
	 * 
	 * @param locator
	 * @return
	 */

	public List<WebElement> getElementsList(final String locator, WebDriver driver) {
		String[] loc = locator.split(":", 2);
		List<WebElement> element = null;
		try {

			if (loc[0].toUpperCase().equalsIgnoreCase("CSS")) {
				element = driver.findElements(By.cssSelector(loc[1]));
			} else {
				element = driver.findElements(By.xpath(loc[1]));
			}

		} catch (Exception e) {
			System.out.println();

		}

		return element;
	}

	/**
	 * the method takes parameter as
	 * 
	 * @param driver
	 * @param byResolution and scroll down the window.
	 */
	public void scrollDownPage(WebDriver driver, int byResolution) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		String scroll = "window.scrollBy(0," + byResolution + ")";
		js.executeScript(scroll);

	}

	/**
	 * method name-scrollToElement
	 * 
	 * @param driver
	 * @param locator
	 */
	public void scrollToElement(WebDriver driver, String locator) {
		locatorsType(locator, driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	
	
	
	public void scrollToDown(WebDriver driver, String locator) {
		locatorsType(locator, driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(false);", element);
	}

	/**
	 * 
	 * method name -wait
	 */
	public void wait(int timeOut) {
		try {
			Thread.sleep(timeOut);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * method name-getElement
	 * 
	 * @param locator
	 * @return
	 */
	public WebElement getElement(String locator, WebDriver driver) {

		locatorsType(locator, driver);
		return element;
	}

	/**
	 * method name-mouseHover
	 * this method is for mouse hover on element
	 * @param locator
	 */
	public void mouseHover(String locator, WebDriver driver) {
		Actions action = new Actions(driver);
		locatorsType(locator, driver);
		action.moveToElement(element).perform();
	}

	/**
	 * method name-propertyFileLoad
	 * this is common method using which i am loading the property file
	 * @param filePath
	 * @return
	 * @throws IOException
	 */

	public Properties propertyFileLoad(String filePath) throws IOException {
		Properties propertyFile = new Properties();
		FileInputStream input = new FileInputStream(filePath);
		propertyFile.load(input);
		return propertyFile;
	}
/**
 * this method is to get inner text using java script executor
 * @param driver
 * @param locators
 */
	public void javascriptGetText(WebDriver driver, String locators) {
		locatorsType(locators, driver);

		JavascriptExecutor execute = (JavascriptExecutor) driver;

		@SuppressWarnings("unused")
		Object texts = execute.executeScript("return document.querySelector('loctor value').innerText;").toString();
	
	}

	/**
	 * Method gets the title of the current page
	 */
	public String getTitle(WebDriver driver) {
		String title = driver.getTitle();
		return title;
	}



}
