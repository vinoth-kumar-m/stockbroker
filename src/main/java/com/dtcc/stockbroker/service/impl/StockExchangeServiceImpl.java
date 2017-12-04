
package com.dtcc.stockbroker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.dtcc.stockbroker.exception.StockBrokerException;
import com.dtcc.stockbroker.model.Stock;
import com.dtcc.stockbroker.service.StockExchangeService;

/**
 * StockExchangeServiceImpl.class - This class provides implementation for StockExchangeService
 * @author Vinoth
 *
 */
@Service("stockExchangeService")
public class StockExchangeServiceImpl implements StockExchangeService {
	
	@Value("${stockexchange.rest.refresh.url}")
	private String refreshUrl;
	
	@Value("${stockexchange.rest.stocks.url}")
	private String stocksUrl;
	
	@Value("${stockexchange.rest.sell.url}")
	private String sellUrl;
	
	@Value("${stockexchange.rest.buy.url}")
	private String buyUrl;
	
	@Value("${stockexchange.rest.performance.url}")
	private String performanceUrl;
	
	private RestTemplate template;
	
	public StockExchangeServiceImpl() {
		template = new RestTemplate();
	}

	/* (non-Javadoc)
	 * @see com.dtcc.stockbroker.service.StockExchangeService#refreshStockRates()
	 */
	@Override
	public List<Stock> refreshStockRates() throws StockBrokerException {
		try {
			ResponseEntity<List<Stock>> response =  template.exchange(refreshUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Stock>>() {});
			if(HttpStatus.OK.equals(response.getStatusCode())) {
				return response.getBody();
			} else {
				throw new StockBrokerException("Error while retrieving refreshed stock details from Stock Exchange");
			}
		} catch(HttpClientErrorException  | IllegalStateException ex) {
			throw new StockBrokerException(ex.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.dtcc.stockbroker.service.StockExchangeService#getStocks()
	 */
	@Override
	public List<Stock> getStocks() throws StockBrokerException {
		try {
			ResponseEntity<List<Stock>> response =  template.exchange(stocksUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Stock>>() {});
			if(HttpStatus.OK.equals(response.getStatusCode())) {
				return response.getBody();
			} else {
				throw new StockBrokerException("Error while retrieving stock details from Stock Exchange");
			}
		} catch(HttpClientErrorException  | IllegalStateException ex) {
			throw new StockBrokerException(ex.getMessage());
		} 
	}

	/* (non-Javadoc)
	 * @see com.dtcc.stockbroker.service.StockExchangeService#sellStock(com.dtcc.stockbroker.model.Stock)
	 */
	@Override
	public void sellStock(Stock stock) throws StockBrokerException {
		try {
			ResponseEntity<String> response =  template.exchange(sellUrl, HttpMethod.POST,  new HttpEntity<>(stock),  String.class);
			if(!HttpStatus.OK.equals(response.getStatusCode())) {
				throw new StockBrokerException("Error while selling stock to Stock Exchange");
			}
		} catch(HttpClientErrorException  | IllegalStateException ex) {
			throw new StockBrokerException(ex.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.dtcc.stockbroker.service.StockExchangeService#buyStock(com.dtcc.stockbroker.model.Stock)
	 */
	@Override
	public void buyStock(Stock stock) throws StockBrokerException {
		try {
			ResponseEntity<String> response =  template.exchange(buyUrl, HttpMethod.POST,  new HttpEntity<>(stock),  String.class);
			if(!HttpStatus.OK.equals(response.getStatusCode())) {
				throw new StockBrokerException("Error while buying stock from Stock Exchange");
			}
		} catch(HttpClientErrorException  | IllegalStateException ex) {
			throw new StockBrokerException(ex.getMessage());
		} catch(Exception ex) {
			throw new StockBrokerException(ex.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.dtcc.stockbroker.service.StockExchangeService#getStockPerformance(java.lang.String)
	 */
	@Override
	public List<Stock> getStockPerformance(String symbol) throws StockBrokerException {
		try {
			ResponseEntity<List<Stock>> response =  template.exchange(performanceUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Stock>>() {}, symbol);
			if(HttpStatus.OK.equals(response.getStatusCode())) {
				return response.getBody();
			} else {
				throw new StockBrokerException("Error while retrieving stock performance from Stock Exchange");
			}
		} catch(HttpClientErrorException  | IllegalStateException ex) {
			throw new StockBrokerException(ex.getMessage());
		}
	}
}
