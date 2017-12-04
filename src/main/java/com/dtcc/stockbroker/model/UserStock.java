
package com.dtcc.stockbroker.model;

import java.io.Serializable;
import java.sql.Date;


/**
 * User.java - a simple modal class to hold information about a stock hold by a user
 * @author Vinoth
 *
 */
public class UserStock extends Stock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date buyDate;
	private Double price;
	
	/**
	 * @return the buyDate
	 */
	public Date getBuyDate() {
		return buyDate;
	}
	/**
	 * @param buyDate the buyDate to set
	 */
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
}
