package com.moshood.services;

import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.moshood.exception.UserNotFoundException;
import com.moshood.model.Account;
import com.moshood.repository.AccountRepository;

@Service
public class AccountService  {
	private final AccountRepository accountRepository;
	private final Logger logger = Logger.getLogger("Account Logger");
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	public long createAccount(String userName) {
		Account account = new Account();
		account.setUsername(userName);
		
		accountRepository.save(account);
		logger.info("Your id is " + account.getId());
		return account.getId();
	}
	
	public Account getAccount(String username)throws UserNotFoundException {
		return accountRepository.findByUsername(username);
	}
	
	public long getAccountId(String username) throws UserNotFoundException {
		Account account = this.getAccount(username);
		return account.getId();
	}
		

}
