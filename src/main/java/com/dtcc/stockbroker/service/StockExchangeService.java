
package com.dtcc.stockbroker.service;

import java.util.List;

import com.dtcc.stockbroker.exception.StockBrokerException;
import com.dtcc.stockbroker.model.Stock;

/**
 * StockExchangeService.java - this is a service class which interact with stock exchange application and process all the webservice calls
 * @author Vinoth
 *
 */

public interface StockExchangeService {
	
	/**
	 * This method helps to refresh price of all the stocks in stock exchange and return the stocks with update price
	 * @return list of all the stocks from stock exchange
	 * @throws StockBrokerException
	 */
	List<Stock> refreshStockRates() throws StockBrokerException;

	/**
	 * This method helps to retrieve all the stocks in stock exchange
	 * @return list of all the stocks
	 * @throws StockBrokerException
	 */
	List<Stock> getStocks() throws StockBrokerException;
	
	/**
	 * This method helps to buy stock from stock exchange
	 * @param stock A variable of type Stock
	 * @throws StockBrokerException
	 */
	void buyStock(Stock stock) throws StockBrokerException;

	/**
	 * This method helps to sell stock to stock exchangee
	 * @param stock A variable of type Stock
	 * @throws StockBrokerException
	 */
	void sellStock(Stock stock) throws StockBrokerException;
	
	/**
	 * This method helps to retrieve stock performance of the given stock from stock exchange
	 * @return history of given stock
	 * @throws StockBrokerException
	 */
	List<Stock> getStockPerformance(String symbol) throws StockBrokerException;
}
