package com.phptravels.util;

/**
 * this class i m handling null value exception.
 * 
 * @author Mohit.Jaiswal
 *
 */

public class NullCellValueException extends Throwable {

	private static final long serialVersionUID = 1L;

	public NullCellValueException(String message) {
		System.out.println(message);
	}

}
