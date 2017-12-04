/**
 * 
 */
package com.dtcc.stockbroker.unittest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dtcc.stockbroker.dao.UserMapper;
import com.dtcc.stockbroker.exception.StockBrokerException;
import com.dtcc.stockbroker.model.User;
import com.dtcc.stockbroker.service.UserService;
import com.dtcc.stockbroker.service.impl.UserServiceImpl;

/**
 * @author Sri
 *
 */
public class UserServiceTest {
	
	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserMapper userMapper;
	
	private User user;
	
	@Before
    public void setupMock() {
		user = new User();
		user.setUsername("vinoth");
        user.setPassword("xxxxxx");
        user.setRole("Investor");
        user.setName("vinoth");
        user.setAddress("Address");
        user.setCity("city");
        user.setPhoneno("123456");
		userService = new UserServiceImpl();
		MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testMockCreation(){
		assertNotNull(user);
		assertNotNull(userMapper);
		assertNotNull(userService);
	}
	
	@Test
	public void testRegisterUser() throws StockBrokerException {
		System.out.println("Stubbing checkUserAlreadyExists to return null");
        when(userMapper.checkUserAlreadyExists(user.getUsername())).thenReturn(null);
        
        System.out.println("Stubbing registerUser to do nothing");
        doNothing().when(userMapper).registerUser(user);
        
        System.out.println("Calling UserService.registerUser()");
        userService.registerUser(user);
	}
	
	@Test(expected = StockBrokerException.class)
    public void userAlreadyExists() throws StockBrokerException {
		System.out.println("--------------------------------------------------------------");
		System.out.println("Stubbing checkUserAlreadyExists to return user object");
        when(userMapper.checkUserAlreadyExists(user.getUsername())).thenReturn(user);
        try {
            System.out.println("UserService.registerUser() throw StockBrokerException");
            userService.registerUser(user);
        } catch (StockBrokerException e) {
            System.out.println("StockBrokerException has been thrown");
            verify(userMapper, times(0)).registerUser(user);
            System.out.println("Verified userMapper.registerUser() is not called");
            throw e;
        }
    }
}
