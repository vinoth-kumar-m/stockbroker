
package com.dtcc.stockbroker.exception;

/**
 * StockBrokerException - This class implements exception to handle all the application related exceptions
 * @author Vinoth
 *
 */
public class StockBrokerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public StockBrokerException(String message) {
		super(message);
		this.message = message;
	}
	
	@Override
	public String toString() {
		return message;
	}

}
