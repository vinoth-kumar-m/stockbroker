/**
 * 
 */
package com.dtcc.stockbroker.unittest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dtcc.stockbroker.dao.PortfolioMapper;
import com.dtcc.stockbroker.exception.StockBrokerException;
import com.dtcc.stockbroker.model.Stock;
import com.dtcc.stockbroker.service.PortfolioService;
import com.dtcc.stockbroker.service.StockExchangeService;
import com.dtcc.stockbroker.service.impl.PortfolioServiceImpl;

/**
 * @author Sri
 *
 */
public class PortfolioServiceTest {
	
	@InjectMocks
	private PortfolioService portfolioService;
	
	@Mock
	private StockExchangeService stockExchangeService;
	
	@Mock
	private PortfolioMapper portfolioMapper;
	
	private Stock stock;
	
	@Before
    public void setupMock() {
		stock = new Stock();
		stock.setId("2");
		stock.setSymbol("SBIN");
		stock.setQuantity(12);
		stock.setMarketPrice(2232.34);
		
		portfolioService = new PortfolioServiceImpl();
		MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testMockCreation(){
		assertNotNull(stock);
		assertNotNull(stockExchangeService);
		assertNotNull(portfolioMapper);
		assertNotNull(portfolioService);
	}
	
	@Test
    public void testBuyStock() throws StockBrokerException {
		System.out.println("--------------------------------------------------------------");
	
        System.out.println("Stubbing StockExchangeService buyStock");
        doNothing().when(stockExchangeService).buyStock(stock);
        
        System.out.println("Stubbing PortfolioMapper buyStock");
        doNothing().when(portfolioMapper).buyStock(stock);
        
        portfolioService.buyStock(stock);
      
        System.out.println("Verified stockExchangeService.buyStock is called atleast once");
        verify(stockExchangeService, atLeastOnce()).buyStock(stock);
        System.out.println("Verified portfolioMapper.buyStock is called atleast once");
        verify(portfolioMapper, atLeastOnce()).buyStock(stock);
    }
	
	@Test
    public void testSellStock() throws StockBrokerException {
		System.out.println("--------------------------------------------------------------");
	
        System.out.println("Stubbing StockExchangeService sellStock");
        doNothing().when(stockExchangeService).sellStock(stock);
        
        System.out.println("Stubbing PortfolioMapper buyStock");
        doNothing().when(portfolioMapper).sellStock(stock);
        
        portfolioService.sellStock(stock);
      
        System.out.println("Verified stockExchangeService.sellStock is called atleast once");
        verify(stockExchangeService, atLeastOnce()).sellStock(stock);
        System.out.println("Verified portfolioMapper.sellStock is called atleast once");
        verify(portfolioMapper, atLeastOnce()).sellStock(stock);
    }
}
