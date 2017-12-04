
package com.dtcc.stockbroker.service;

import java.util.List;

import com.dtcc.stockbroker.exception.StockBrokerException;
import com.dtcc.stockbroker.model.User;

/**
 * UserService.java - this is a service class which handles all the requests related to users
 * @author Vinoth
 *
 */
public interface UserService {
	
	/**
	 * This method will check whether user is authenticated to access the application
	 * @param user A variable of type User
	 * @return user details
	 * @throws StockBrokerException
	 */
	User login(User user) throws StockBrokerException;
	
	/**
	 * This method will help to register new user into the application
	 * @param user A variable of type User
	 * @throws StockBrokerException
	 */
	void registerUser(User user) throws StockBrokerException;

	/**
	 * This method will retrieve all the user details of the application
	 * @return list of all users
	 * @throws StockBrokerException
	 */
	List<User> getUsers() throws StockBrokerException;
}
