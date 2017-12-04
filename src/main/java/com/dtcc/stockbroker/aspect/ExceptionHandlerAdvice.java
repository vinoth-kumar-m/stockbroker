
package com.dtcc.stockbroker.aspect;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dtcc.stockbroker.exception.StockBrokerException;


/**
 * Error.java - a simple modal class to hold error code and message during exception scenario
 * @author Vinoth
 *
 */

class Error {
	
	int code;
	String message;
	
	/**
	 * Parameterized constructor to initialize code and message
	 * @param code A variable of type int
	 * @param message
	 */
	public Error(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	/**
	 * Retrieve the value of code
	 * @return A int datatype
	 */
	public int getCode() {
		return code;
	}
	
	/**
	 * Set the value for code
	 * @param code A variable of type int
	 */
	public void setCode(int code) {
		this.code = code;
	}
	
	/**
	 * Retrieve the value of message
	 * @return A String datatype
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Set the value for message
	 * @param message A variable of type String
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}

/**
 * ExceptionHandlerAdvice.java - this class will handle all exceptions thrown from stockbroker application
 * @author Vinoth
 *
 */

@ControllerAdvice
public class ExceptionHandlerAdvice {
	private static final int APPLICATION_EXCEPTION = 100;
	
	/**
	 * This method handles all StockBrokerException and consturct Error object from exception message 
	 * @param req  A variable of type HttpServletRequest
	 * @param ex A variable  of type StockBrokerException
	 * @return a ResponseEntity type with Error object
	 */
	@ExceptionHandler(StockBrokerException.class)
	public ResponseEntity<Error> handleApplicationException(HttpServletRequest req, StockBrokerException ex) {
		return new ResponseEntity<Error>(new Error(APPLICATION_EXCEPTION, ex.getMessage()), HttpStatus.EXPECTATION_FAILED);
	}
	
	/**
	 * This method handles all generic exceptions and consturct Error object from exception message 
	 * 			and return ResponseEntity with Error object
	 * @param req  A variable of type HttpServletRequest
	 * @param ex A variable  of type Exception
	 * @return a ResponseEntity type with Error object
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Error> handleGenericExcaption(HttpServletRequest req, Exception ex) {
		System.out.println("Stock Broker Generic Exception Handler: " + ex.getMessage());
		return new ResponseEntity<Error>(new Error(APPLICATION_EXCEPTION, ex.getMessage()), HttpStatus.EXPECTATION_FAILED);
	}
}
