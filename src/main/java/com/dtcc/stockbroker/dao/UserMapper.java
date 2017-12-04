
package com.dtcc.stockbroker.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.dtcc.stockbroker.model.User;

/**
 * UserMapper.java - This is mapper interface handle all database calls related to users
 * @author Vinoth
 *
 */
public interface UserMapper {
	
	/**
	 * This method retrieve user details of the given username from database
	 * @param username A variable of type String
	 * @param password A variable of type String
	 * @return A variable of type User 
	 */
	@Select("SELECT id, username, name, role, address, city, phoneno FROM users WHERE username = #{username} AND password = #{password}")
	User login(@Param("username") String username, @Param("password") String password);
	
	/**
	 * This method checks whether the given user details already exists in the database
	 * @param username A variable of type String
	 * @return A variable of type User 
	 */
	@Select("SELECT id, username, name, role, address, city, phoneno FROM users WHERE username = #{username}")
	User checkUserAlreadyExists(@Param("username") String username);
	
	/**
	 * This method retrieve all user details from database
	 * @return list of all users
	 */
	@Select("SELECT id, username, name, address, city, phoneno FROM users WHERE role='Investor'")
	List<User> getUsers();
	
	/**
	 * This method will insert users details into database
	 * @param user A variable of type User
	 */
	@Insert("INSERT into users VALUES(user_id_seq.nextval, #{username}, #{password}, 'Investor', #{name}, #{address}, #{city}, #{phoneno})")
	void registerUser(User user);
}
