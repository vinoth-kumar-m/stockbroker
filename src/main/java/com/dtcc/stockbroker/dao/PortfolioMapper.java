
package com.dtcc.stockbroker.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dtcc.stockbroker.model.Stock;
import com.dtcc.stockbroker.model.UserStock;

/**
 * PortfolioMapper.java - This is mapper interface handle all database calls for Portfolio screen
 * @author Vinoth
 *
 */
public interface PortfolioMapper {
	
	/**
	 * This method will retrieve the stock details of the given user from database
	 * @param username A variable of type String
	 * @return list of stocks holding by the user
	 */
	@Results(id = "userStocks", value = {
			  @Result(property = "id", column = "id"),
			  @Result(property = "symbol", column = "symbol"),
			  @Result(property = "buyDate", column = "buy_date"),
			  @Result(property = "quantity", column = "quantity"),
			  @Result(property = "price", column = "price")
			})
	@Select("SELECT * FROM user_stocks WHERE id = (SELECT id FROM users WHERE username = #{username}) AND status = 'ACTIVE'")
	List<UserStock> getUserStocks(String username);
	
	/**
	 * This method will insert stock buying details of the user into database
	 * @param stock A variable of type Stock
	 */
	@Insert("INSERT into user_stocks VALUES(#{id}, #{symbol}, sysdate, #{quantity}, #{marketPrice}, 'ACTIVE')")
	void buyStock(Stock stock);
	
	/**
	 * This method will update stock selling details of the user into database
	 * @param stock
	 */
	@Update("UPDATE user_stocks SET quantity=quantity-#{quantity}, status=DECODE(quantity - #{quantity}, 0, 'CLOSED', 'ACTIVE') WHERE id=#{id} AND symbol=#{symbol}")
	void sellStock(Stock stock);
}
