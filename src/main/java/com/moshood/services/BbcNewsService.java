package com.moshood.services;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.moshood.apiDomain.NewsResponse;
import com.moshood.config.BalanceWebSocketHandler;
import com.moshood.exception.UserNotFoundException;
import com.moshood.model.Account;
import com.moshood.observer.LogInComponent;
import com.moshood.repository.AccountRepository;
import com.moshood.servicesInterface.NewsService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service 
public class BbcNewsService implements NewsService {
	
//	private static final String API_KEY = "";
	private static final String NEWS_URL = "https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=b59feca5c01748a6aa8f1c79cb51e66c";
	private final RestTemplate restTemplate;
	
	private final AccountRepository accountRepository;
	private final BalanceService balanceService;
	private final BalanceWebSocketHandler handler;
	private final Logger logger = Logger.getLogger("Bbc newsService");
	@Transactional
	public Optional<NewsResponse> fetchTopHeadlines(long id) {
	    BigDecimal balance;
	    double cost = 10;
	    
	    try {
	    	logger.info("from the service is " + id);
	    	Account account = accountRepository.findById(id).get();
	    	String username = account.getUsername();
	        balance = account.getBalance();
	        
	        if (balance.compareTo(BigDecimal.valueOf(cost)) >= 0) {
	            ResponseEntity<NewsResponse> response = restTemplate.getForEntity(NEWS_URL, NewsResponse.class);
	            BigDecimal newBalance = balance.subtract(BigDecimal.valueOf(cost));
	            accountRepository.updateBalance(id, newBalance);
	            handler.sendBalanceUpdate(username, newBalance);
	            return Optional.ofNullable(response.getBody());
	        } else {
	            return Optional.empty(); // Not enough balance
	        }
	    } catch (NoSuchElementException e) {
	        e.printStackTrace(); // Consider logging instead of printing
	    } catch (Exception e) {
	        e.printStackTrace(); // Catch unexpected exceptions
	    }

	    return Optional.empty();
	}

	
}
