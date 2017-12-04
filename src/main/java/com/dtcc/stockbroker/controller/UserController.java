/**
 * 
 */
package com.dtcc.stockbroker.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dtcc.stockbroker.exception.StockBrokerException;
import com.dtcc.stockbroker.model.User;
import com.dtcc.stockbroker.service.UserService;

/**
 * UserController.java - this is a controller class which receives all user related requests and process it
 * @author Vinoth
 *
 */

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	/**
	 * This method authenticate user details 
	 * @param input A varilable of type User
	 * @param request A variable of type HttpServletRequest
	 * @return a RequestEntity object with user details
	 * @throws StockBrokerException
	 */
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody User input, HttpServletRequest request) throws StockBrokerException {
		User user = userService.login(input);
		if(user == null) {
			return new ResponseEntity<Map<String, String>>(Collections.singletonMap("message", "Invalid login credentials"), HttpStatus.UNAUTHORIZED);
		}
		request.getSession().setAttribute("user", user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	/**
	 * This method helps to register new user details
	 * @param input A varilable of type User
	 * @param request A variable of type HttpServletRequest
	 * @return a RequestEntity object with message
	 * @throws StockBrokerException
	 */
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody User input, HttpServletRequest request) throws StockBrokerException {
		userService.registerUser(input);
		return new ResponseEntity<Map<String, String>>(Collections.singletonMap("message", "Registration Successful"), HttpStatus.OK);
	}

	/**
	 * This method will retrieve all the user details
	 * @return a RequestEntity object all user details
	 * @throws StockBrokerException
	 */
	@RequestMapping(value="/users", method = RequestMethod.GET)
	public ResponseEntity<?> getUsers()  throws StockBrokerException {
		List<User> users = userService.getUsers();
		return new ResponseEntity<Map<String, List<User>>>(Collections.singletonMap("users", users), HttpStatus.OK);
	}
	
	/**
	 * This method invalide the user session and logout the useer
	 * @return a RequestEntity object with message
	 * @throws StockBrokerException
	 */
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public ResponseEntity<?> logout(HttpServletRequest request)  throws StockBrokerException {
		request.getSession().invalidate();
		return new ResponseEntity<Map<String, String>>(Collections.singletonMap("message", "User logged out successfully"), HttpStatus.OK);
	}
}
