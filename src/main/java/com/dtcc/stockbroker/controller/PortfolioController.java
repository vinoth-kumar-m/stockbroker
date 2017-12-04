
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dtcc.stockbroker.exception.StockBrokerException;
import com.dtcc.stockbroker.model.Stock;
import com.dtcc.stockbroker.model.User;
import com.dtcc.stockbroker.model.UserStock;
import com.dtcc.stockbroker.service.PortfolioService;


/**
 * PortfolioController.java - this is a controller class which receives all the requests from Portfolio screen and process it
 * @author Vinoth
 *
 */

@Controller
public class PortfolioController {

	@Autowired
	private PortfolioService portfolioService;

	/**
	 * This method returns the portfolio details of the given user
	 * @param username A variable of type String
	 * @param request A variable of type HttpServletRequest
	 * @return A RequestEntity object with user stock details
	 * @throws StockBrokerException
	 */
	@RequestMapping(value = "/portfolio/{username}", method = RequestMethod.GET)
	public ResponseEntity<?> getUserStocks(@PathVariable("username") String username, HttpServletRequest request)
			throws StockBrokerException {
		List<UserStock> userStocks = portfolioService.getUserStocks(username);
		return new ResponseEntity<Map<String, List<UserStock>>>(Collections.singletonMap("userStocks", userStocks),
				HttpStatus.OK);
	}

	/**
	 * This method process sell requests from user
	 * @param stock A variable of type Stock
	 * @param request A variable of type HttpServletRequest
	 * @return A RequestEntity object with message
	 * @throws StockBrokerException
	 */
	@RequestMapping(value = "/sellStock", method = RequestMethod.POST)
	public ResponseEntity<?> sellStock(@RequestBody Stock stock, HttpServletRequest request)
			throws StockBrokerException {
		portfolioService.sellStock(stock);
		return new ResponseEntity<Map<String, String>>(Collections.singletonMap("message", "Stock sold successfully"),
				HttpStatus.OK);
	}

	/**
	 * This method process buy requests from user
	 * @param stock A variable of type Stock
	 * @param request A variable of type HttpServletRequest
	 * @return A RequestEntity object with message
	 * @throws StockBrokerException
	 */
	@RequestMapping(value = "/buyStock", method = RequestMethod.POST)
	public ResponseEntity<?> buyStock(@RequestBody Stock stock, HttpServletRequest request)
			throws StockBrokerException {
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			stock.setId(user.getId());
		}
		portfolioService.buyStock(stock);
		return new ResponseEntity<Map<String, String>>(Collections.singletonMap("message", "Stock buy successfully"),
				HttpStatus.OK);

	}
}
