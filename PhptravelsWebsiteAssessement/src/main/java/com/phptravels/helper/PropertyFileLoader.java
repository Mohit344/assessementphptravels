package com.phptravels.helper;

import java.io.IOException;
import java.util.Properties;

import com.phptravels.constants.FilePath;

/**
 *load the all the property files of the project.
 * 
 * @author Mohit.Jaiswal
 *
 */
public class PropertyFileLoader {
	CommonUtility common = new CommonUtility();

    Properties config;
    Properties phpTravells;
    Properties pageValidation;
	
   
	public PropertyFileLoader() {

	try {

		// load the property file config.properties
		config = common.propertyFileLoad(FilePath.CONFIG_FILE);
	} catch (IOException e) {
		e.getMessage();
	
	}

	try {

		// load the property file phplocators.properties
		phpTravells = common.propertyFileLoad(FilePath.PHPTRAVELL_LOCATOR);
	} catch (IOException e) {
		e.getMessage();
	
	}


	
	try {

		// load the property file phplocators.properties
		pageValidation = common.propertyFileLoad(FilePath.VALIDATION_FILE);
	} catch (IOException e) {
		e.getMessage();
	
	}

	
	
	
}
	
	
	
	
	

	
	

	

	/**
	 * method-getConfig
	 * this method load load  the config file for browser configuration
	 * @param key
	 * @return
	 */
	
	public String getConfig(String key) {
		String value = config.getProperty(key);
		return value;

	}
	/**
	 * 
	 * @param key
	 * @return
	 */

	public String locator(String key) {
		String value = phpTravells.getProperty(key);
		return value;

	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	

	public String validation(String key) {
		String value = pageValidation.getProperty(key);
		return value;

	}
	
	
}
