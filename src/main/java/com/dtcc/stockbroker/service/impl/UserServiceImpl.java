
package com.dtcc.stockbroker.service.impl;

import java.util.List;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dtcc.stockbroker.dao.UserMapper;
import com.dtcc.stockbroker.exception.StockBrokerException;
import com.dtcc.stockbroker.model.User;
import com.dtcc.stockbroker.service.UserService;

/**
 * UserServiceImpl.class - This class provides implementation for UserService
 * @author Vinoth
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	/* (non-Javadoc)
	 * @see com.dtcc.stockbroker.service.LoginService#login(com.dtcc.stockbroker.model.User)
	 */
	@Override
	public User login(User user) throws StockBrokerException {
		try {
			return userMapper.login(user.getUsername(), user.getPassword());
		} catch(MyBatisSystemException ex) {
			throw new StockBrokerException(ex.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.dtcc.stockbroker.service.UserService#registerUser(com.dtcc.stockbroker.model.User)
	 */
	@Override
	public void registerUser(User user) throws StockBrokerException {
		User userInDB = null;
		try {
			userInDB = userMapper.checkUserAlreadyExists(user.getUsername());
			if(userInDB != null) {
				throw new StockBrokerException("User already exists");
			}
			userMapper.registerUser(user);
		} catch(MyBatisSystemException ex) {
			throw new StockBrokerException(ex.getMessage());
		}
	}



	/* (non-Javadoc)
	 * @see com.dtcc.stockbroker.service.UserService#getUsers()
	 */
	@Override
	@Cacheable("usersCache")
	public List<User> getUsers() throws StockBrokerException {
		try {
			return userMapper.getUsers();
		} catch(MyBatisSystemException ex) {
			throw new StockBrokerException(ex.getMessage());
		}
	}

}
