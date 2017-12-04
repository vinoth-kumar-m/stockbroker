
package com.dtcc.stockbroker.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dtcc.stockbroker.exception.StockBrokerException;
import com.dtcc.stockbroker.model.Stock;
import com.dtcc.stockbroker.service.StockExchangeService;

/**
 * StockExchangeController.java - this is a controller class which receives all the requests from Stocks screen and process it
 * @author Vinoth
 *
 */

@Controller
public class StockExchangeController {
	
	@Autowired
	private StockExchangeService stockExchangeService;
	
	/**
	 * This method will refresh price of all the stocks and return stocks with updated price
	 * @param request A variable of type HttpServletRequest
	 * @return a RequestEntity object with all stock details
	 * @throws StockBrokerException
	 */
	@RequestMapping(value="/refresh", method = RequestMethod.GET)
	public ResponseEntity<?> refreshStockRates(HttpServletRequest request)  throws StockBrokerException {
		List<Stock> stocks = stockExchangeService.refreshStockRates();
		return new ResponseEntity<Map<String, List<Stock>>>(Collections.singletonMap("stocks", stocks), HttpStatus.OK);
	}
	
	/**
	 * This method will retrieve all stock details from stock exchange
	 * @param request A variable of type HttpServletRequest
	 * @return a RequestEntity object with all stock details
	 * @throws StockBrokerException
	 */
	@RequestMapping(value="/getStocks", method = RequestMethod.GET)
	public ResponseEntity<?> getStocks(HttpServletRequest request) throws StockBrokerException {
		List<Stock> stocks = stockExchangeService.getStocks();
		return new ResponseEntity<Map<String, List<Stock>>>(Collections.singletonMap("stocks", stocks), HttpStatus.OK);
	}
	
	/**
	 * This method will retrieve the performance of the given stock
	 * @param symbol A variable of type String
	 * @param request A variable of type HttpServletRequest
	 * @return A RequestEntity object with stock history
	 * @throws StockBrokerException
	 */
	@RequestMapping(value="/stockPerformance/{symbol}", method = RequestMethod.GET)
	public ResponseEntity<?> getStockPerformance(@PathVariable("symbol") String symbol, HttpServletRequest request)  throws StockBrokerException {
		List<Stock> history = stockExchangeService.getStockPerformance(symbol);
		return new ResponseEntity<Map<String, List<Stock>>>(Collections.singletonMap("historicalData", history), HttpStatus.OK);
	}
	
}
