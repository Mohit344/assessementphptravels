package com.phptravels.logreport;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.phptravels.constants.FilePath;



/**
 * In this class, log reports is made.
 */
public class LogReport {
	public Logger logger = null;

	/*
	 * constructor used to get the logger to load the configuration file
	 */
	public LogReport() {

		// calling the method, to load the config file
		getlogger();
		logger = Logger.getLogger(LogReport.class.getName());
	}

	/**
	 * method loads the config file from the fileconstant
	 */
	public static void getlogger() {
		PropertyConfigurator.configure(FilePath.Log4j);
	}

	/**
	 * the method takes input as string message
	 * 
	 * @param message is printed on the console
	 */
	public void info(String message) {
		logger.info(message);

	}
}
