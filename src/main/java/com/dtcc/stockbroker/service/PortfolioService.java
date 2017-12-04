
package com.dtcc.stockbroker.service;

import java.util.List;

import com.dtcc.stockbroker.exception.StockBrokerException;
import com.dtcc.stockbroker.model.Stock;
import com.dtcc.stockbroker.model.UserStock;


/**
 * PortfolioService.java - this is a service class which handles all the requests from Portfolio screen
 * @author Vinoth
 *
 */
public interface PortfolioService {
	
	/**
	 * This method interacts with stock exchange and returns the portfolio details of the given user with market price of current holdings
	 * @param A variable of type String
	 * @return list of all user stocks
	 * @throws StockBrokerException
	 */
	List<UserStock> getUserStocks(String username) throws StockBrokerException;

	/**
	 * This method interacts with stock exchange for the stock availability and provision the user to buy the stock
	 * @param stock A variable of type Stock
	 * @throws StockBrokerException
	 */
	void buyStock(Stock stock)  throws StockBrokerException;

	/**
	 * This method interacts with stock exchange and provision the user to sell the stock
	 * @param stock A variable of type Stock
	 * @throws StockBrokerException
	 */
	void sellStock(Stock stock)  throws StockBrokerException;
}
