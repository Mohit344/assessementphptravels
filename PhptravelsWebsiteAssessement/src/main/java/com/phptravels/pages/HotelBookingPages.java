package com.phptravels.pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.log4testng.Logger;

import com.phptravels.constants.FilePath;
import com.phptravels.helper.CommonUtility;
import com.phptravels.helper.PropertyFileLoader;
import com.phptravels.logreport.LogReport;
import com.phptravels.util.NullCellValueException;
import com.phptravels.util.ReadExcelFile;
import com.phptravels.util.WriteExcelOperation;
import com.phptravels.validation.VerificationManager;
import com.phptravels.waits.Waits;

/**
 * Class validates the different functionality of phptravels website
 * 
 * @author Mohit.Jaiswal
 *
 */
public class HotelBookingPages {

	LogReport log = new LogReport();
	
	CommonUtility utility = new CommonUtility();
	PropertyFileLoader properties = new PropertyFileLoader();
	VerificationManager validate = new VerificationManager();
	ReadExcelFile excelReader = new ReadExcelFile();
	Logger logger = Logger.getLogger(HotelBookingPages.class);
	WriteExcelOperation writeOperation = new WriteExcelOperation();
	Waits time = new Waits();
	LocalDate date = LocalDate.now();

	public void navigateToHomePage(WebDriver driver) {
		log.logger.info("verifying the user it navigated to home page or not");
		String actualTitle = utility.getTitle(driver);

		validate.verify(actualTitle, properties.validation("dashBoardPageTitle"), "not landed to right page");
		log.logger.info("verified that user landed to right page");

	}

	public void effectiveDate(int numOfDays, int twoDayNext) {

		LocalDate dateTenDayNext = date.plusDays(numOfDays);
		LocalDate dateTwoDayNext = dateTenDayNext.plusDays(twoDayNext);

		String dateAfterTenDays = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH).format(dateTenDayNext);
		String dateNextTwoDay = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH).format(dateTwoDayNext);
		log.logger.info("write the checkin date in the file");
		writeOperation.setCellDataUnique(FilePath.BOOKING_DETAILS, "bookingDetail", "CheckIn", "Test_ID1",
				dateAfterTenDays);
		log.logger.info("write the checkout date in the file");
		writeOperation.setCellDataUnique(FilePath.BOOKING_DETAILS, "bookingDetail", "CheckOut", "Test_ID1",
				dateNextTwoDay);

	}

	public void verifyhotelNavigation(WebDriver driver) {
		log.logger.info("verifying the user it navigated to hotel page or not");
		String actualTitle = utility.getTitle(driver);
		validate.verify(actualTitle, properties.validation("hotelPageTitle"), "not redirected to correct page");
		log.logger.info("verified the user it navigated to hotel page or not");

	}

	public void verifyBookingDetails(WebDriver driver) throws NullCellValueException {

		String destination = utility.getText(driver, properties.locator("loc.txt.destination"));
		
		String travelerCheckIn = utility.getElement(properties.locator("loc.txt.checkin"), driver)
				.getAttribute("value");
		
		
		
		String TravelerCheckOut = utility.getElement(properties.locator("loc.txt.checkout"), driver)
				.getAttribute("value");
		

		String noOfAdultText = utility.getElement(properties.locator("loc.txt.noOfAdult"), driver)
				.getAttribute("value");
		
		String noOfChildText = utility.getElement(properties.locator("loc.txt.noOfChild"), driver)
				.getAttribute("value");
		log.logger.info("bookedChild" + noOfChildText);
		String checkInDate = excelReader.getCellData(FilePath.BOOKING_DETAILS, "bookingDetail", "CheckIn", "Test_ID1");

		String checkOutDate = excelReader.getCellData(FilePath.BOOKING_DETAILS, "bookingDetail", "CheckOut",
				"Test_ID1");
		String numOfAdult = excelReader.getCellData(FilePath.BOOKING_DETAILS, "bookingDetail", "adult", "Test_ID1");
		String numOfChild = excelReader.getCellData(FilePath.BOOKING_DETAILS, "bookingDetail", "child", "Test_ID1");
		log.logger.info(numOfChild);
		
          log.logger.info("verify the distination ");
		validate.verify(destination, properties.validation("hotelDestination"), "destination not correct");

		log.logger.info("verify the checkInDate");
		validate.verify(travelerCheckIn, checkInDate, "check in not correct");

		log.logger.info("verify the checkoutDate ");
		validate.verify(TravelerCheckOut, checkOutDate, "check out incorrect");
		log.logger.info("verify the no of adult");
		validate.verify(noOfAdultText, numOfAdult, "not correct");
		log.logger.info("verify the no of child");
		validate.verify(noOfChildText, numOfChild, "not correct");

		log.logger.info("verified the destination, checkin ,checkout,noofAdult ,noofchild  ");

	}

	public void identifyingLowestPriceHotel(WebDriver driver) throws InterruptedException {

		
		
		
		utility.clickElement(driver, properties.locator("loc.btn.gotIt"));
		
		utility.scrollToDown(driver, properties.locator("loc.scroll.view"));
		utility.clickElement(driver, properties.locator("loc.scroll.view"));
		time.isElementVisible(driver, properties.locator("loc.scroll.view"));
		utility.scrollToDown(driver, properties.locator("loc.scroll.view"));
		utility.clickElement(driver, properties.locator("loc.scroll.view"));
		time.isElementVisible(driver, properties.locator("loc.scroll.view"));
		utility.scrollToDown(driver, properties.locator("loc.scroll.view"));
		utility.clickElement(driver, properties.locator("loc.scroll.view"));
		
	
		List<WebElement> ratingList=utility.getElementsList(properties.locator("loc.list.rating"), driver);
		
		
		List<Double> hotelPriceList= new ArrayList<Double>(ratingList.size());
		List<Double> listOfPrice= new ArrayList<Double>(ratingList.size());
        
		for(int index=0;index<ratingList.size();index++){
		String priceLocator=properties.locator("loc.text.priceList").replace("***",index+1+"");
		String priceText=driver.findElement(By.xpath(priceLocator)).getText();
		String priceList=priceText.replace("USD", "").replaceAll("\\s+", "");
		
		listOfPrice.add(index, Double.parseDouble(priceList));//original
		hotelPriceList.add(index, Double.parseDouble(priceList));
		}
	
		
		
		Collections.sort(hotelPriceList);
		
	     
	
		log.logger.info(hotelPriceList.get(0));
	
		
		

		
	
		int lowestIndex=listOfPrice.indexOf(hotelPriceList.get(0));

	
	String locator=properties.locator("loc.btn.price").replace("***", lowestIndex+1+"");
		utility.scrollToDown(driver, locator);
	
		utility.clickElement(driver, locator);
		
		log.logger.info(lowestIndex);
	}

		
		public void selectCity(WebDriver driver)
		{
			Actions action = new Actions(driver);
			
			WebElement element = driver.findElement(By.cssSelector(properties.locator("loc.select.destination")));

			action.moveToElement(element).build().perform();
			
			element.click();
		}
		
		
		
   

public void hotelVerification(WebDriver driver)
{
	
	String actualName=utility.getText(driver, properties.locator("loc.txt.hotelName"));
	
	log.logger.info("varify the hotelName");
	validate.verify(actualName, properties.validation("hotelName"), "name is not correct");
	
	String actualAddress=utility.getText(driver, properties.locator("loc.txt.hotelAddress"));
	
    log.logger.info("varify the hotelAddress");
	
    validate.verify(actualAddress, properties.validation("hotelAddress"), "address is not matched");
	
	
	
	

	
	
	
}




















}