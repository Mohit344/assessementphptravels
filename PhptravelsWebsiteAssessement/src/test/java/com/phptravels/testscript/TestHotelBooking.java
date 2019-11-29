package com.phptravels.testscript;

import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.phptravels.constants.FilePath;
import com.phptravels.constants.TimeConstant;
import com.phptravels.helper.CommonUtility;
import com.phptravels.helper.PropertyFileLoader;
import com.phptravels.logreport.LogReport;
import com.phptravels.pages.HotelBookingPages;
import com.phptravels.testbase.TestBase;
import com.phptravels.util.NullCellValueException;
import com.phptravels.util.ReadExcelFile;
import com.phptravels.validation.VerificationManager;
import com.phptravels.waits.Waits;

/**
 * In this class i'm maintaining all my testscripts
 * 
 * @author Mohit.Jaiswal
 *
 */
public class TestHotelBooking extends TestBase {
	CommonUtility utility = new CommonUtility();
	PropertyFileLoader properties = new PropertyFileLoader();
	VerificationManager validate = new VerificationManager();
	ReadExcelFile excelReader = new ReadExcelFile();
	HotelBookingPages hotelBooking = new HotelBookingPages();
	LogReport log = new LogReport();
	Waits time = new Waits();
	
	Logger logger = Logger.getLogger(TestHotelBooking.class);
	
	

	@Test

	public void bookingHotel() throws NullCellValueException, InterruptedException

	{
		

		hotelBooking.navigateToHomePage(driver);
		log.logger.info("validated the user landed successfully");
		log.logger.info("click on the hotel tab");
		utility.clickElement(driver, properties.locator("loc.btn.hotelTab"));

		utility.clickElement(driver, properties.locator("loc.select.destinationTab"));

		String bookingDestination = excelReader.getCellData(FilePath.BOOKING_DETAILS, "bookingDetail", "Destination",
				"Test_ID1");
		log.logger.info(" enter the destination data in the input");
		utility.clickAndSendText(driver, properties.locator("loc.input.destination"), TimeConstant.TIME_OUT,
				bookingDestination);

		utility.scrollDownPage(driver, TimeConstant.scroll);

		utility.clickElement(driver, properties.locator("loc.select.destination"));

		log.logger.info("select the bangalore location");

		hotelBooking.selectCity(driver);
		log.logger.info("enter the check in date and check out");
		
		
		String checkInDuration = excelReader.getCellData(FilePath.BOOKING_DETAILS, "bookingDetail", "CheckInDuration", "Test_ID1");
		String checkOutDuration = excelReader.getCellData(FilePath.BOOKING_DETAILS, "bookingDetail", "CheckOutDuration", "Test_ID1");
		int checkIn=Integer.parseInt(checkInDuration);
		int checkOut=Integer.parseInt(checkOutDuration);
		
		
		hotelBooking.effectiveDate(checkIn, checkOut);

		String checkInDate = excelReader.getCellData(FilePath.BOOKING_DETAILS, "bookingDetail", "CheckIn", "Test_ID1");
		String checkOutDate = excelReader.getCellData(FilePath.BOOKING_DETAILS, "bookingDetail", "CheckOut",
				"Test_ID1");

		log.logger.info("input the checkin date");
		utility.clickAndSendText(driver, properties.locator("loc.input.checkin"), TimeConstant.TIME_OUT, checkInDate);
		log.logger.info("input the checkOut date");
		utility.clickAndSendText(driver, properties.locator("loc.input.checkout"), TimeConstant.TIME_OUT, checkOutDate);

		log.logger.info("click the plus btn for no of  adult");
		utility.clickElement(driver, properties.locator("loc.btn.adultplus"));
		utility.clickElement(driver, properties.locator("loc.btn.tab"));
		utility.clickElement(driver, properties.locator("loc.btn.adultplus"));

		log.logger.info("click the btn for no of child ");
		utility.clickElement(driver, properties.locator("loc.btn.childplus"));
		utility.clickElement(driver, properties.locator("loc.btn.tab"));
		utility.clickElement(driver, properties.locator("loc.btn.childplus"));
		log.logger.info("click on the search btn ");
		utility.clickElement(driver, properties.locator("loc.btn.search"));

		log.logger.info("verify the user landed to hotel page or not");
		hotelBooking.verifyhotelNavigation(driver);
		log.logger.info("verified the user landed to hotel page or not");

		log.logger.info("click the modify btn");
		utility.clickElement(driver, properties.locator("loc.btn.modify"));
        
		log.logger.info("varify booking details");
		hotelBooking.verifyBookingDetails(driver);
		
		log.logger.info("varified the booking details");
		log.logger.info(" search on the lowest price hotel");
		hotelBooking.identifyingLowestPriceHotel(driver);
		log.logger.info("clicked on the demo btn navigate to hoteldetails ");
		
		log.logger.info("verify the hotel:name,address");
		hotelBooking.hotelVerification(driver);
		log.logger.info("verified the hotel name and address");
		
		log.logger.info("verify the  booking details -destination,checkin,checkout,no of child,adult ");
		hotelBooking.verifyBookingDetails(driver);
        log.logger.info("verified the booking details of hotel ");
		
	}
}
