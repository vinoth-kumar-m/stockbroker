
package com.dtcc.stockbroker.service.impl;

import java.util.List;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import com.dtcc.stockbroker.dao.PortfolioMapper;
import com.dtcc.stockbroker.exception.StockBrokerException;
import com.dtcc.stockbroker.model.Stock;
import com.dtcc.stockbroker.model.UserStock;
import com.dtcc.stockbroker.service.PortfolioService;
import com.dtcc.stockbroker.service.StockExchangeService;

/**
 * PortfolioServiceImpl.class - This class provides implementation for PortfolioService
 * @author Vinoth
 *
 */
@Service("portfolioService")
public class PortfolioServiceImpl implements PortfolioService {
	
	@Autowired
	private PortfolioMapper portfolioMapper;
	
	@Autowired
	private StockExchangeService stockExchangeService;

	/* (non-Javadoc)
	 * @see com.dtcc.stockbroker.service.PortfolioService#getUserStocks(com.dtcc.stockbroker.model.User)
	 */
	@Override
	public List<UserStock> getUserStocks(String username)  throws StockBrokerException {
		List<UserStock> userStocks = null; 
		try {
			userStocks = portfolioMapper.getUserStocks(username);
			if(userStocks != null && !userStocks.isEmpty()) {
				List<Stock> stocks;
				try {
					stocks = stockExchangeService.getStocks();
				}  catch(ResourceAccessException ex) {
					throw new StockBrokerException("Exception while accessing stock exchange webservice");
				}
				if(stocks != null && !stocks.isEmpty()) {
					userStocks.forEach(e -> {
						int idx = stocks.indexOf(e);
						if(idx != -1) {
							Stock xChgStk = stocks.get(idx);
							e.setName(xChgStk.getName());
							e.setMarketPrice(xChgStk.getMarketPrice());
						}
					});
				}
			}
			return userStocks;
		} catch(ResourceAccessException | MyBatisSystemException ex) {
			throw new StockBrokerException(ex.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.dtcc.stockbroker.service.PortfolioService#buyStock(com.dtcc.stockbroker.model.Stock)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void buyStock(Stock stock)  throws StockBrokerException {
		try {
			portfolioMapper.buyStock(stock);
			stockExchangeService.buyStock(stock);
		} catch(MyBatisSystemException ex) {
			throw new StockBrokerException(ex.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.dtcc.stockbroker.service.PortfolioService#sellStock(com.dtcc.stockbroker.model.Stock)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void sellStock(Stock stock)  throws StockBrokerException {
		try {
			portfolioMapper.sellStock(stock);
			stockExchangeService.sellStock(stock);
		} catch(MyBatisSystemException ex) {
			throw new StockBrokerException(ex.getMessage());
		}
	}

}
