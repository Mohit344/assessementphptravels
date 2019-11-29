package com.phptravels.validation;

import org.testng.Assert;
/**
 *  this class consist of assert methods for validation of data.
 * @author Mohit.Jaiswal
 *
 */

public class VerificationManager {

	/**
	 * 
	 * @param texts
	 * @param expected
	 * @param message
	 */
	public void verify(String texts, String expected, String message) { // String or objects depend
		try {

			Assert.assertEquals(texts, expected, message);
			

		} catch (AssertionError e) {
			System.out.println(message);
			
		}
	}

	public void verifyContent(String actual, String expected, String message) {
		try {

			
			Assert.assertTrue(actual.contains(expected), message);    
			

		} catch (AssertionError e) {
			
		}
	}


}
