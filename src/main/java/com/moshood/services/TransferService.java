package com.moshood.services;

import java.math.BigDecimal;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moshood.config.BalanceWebSocketHandler;
import com.moshood.exception.InsufficientAmountException;
import com.moshood.exception.UserNotFoundException;
import com.moshood.model.Account;
import com.moshood.repository.AccountRepository;

import jakarta.servlet.http.HttpSession;


@Service
@Transactional
public class TransferService  {
	
	private final AccountRepository accountRepository;
	
	
	
	public TransferService (AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
		
		
	}
	private final Logger logger = Logger.getLogger("Transfer Service");

	public void transferMoney(long senderId, long receiverId,BigDecimal amount) throws InsufficientAmountException, UserNotFoundException {
		Account senderAccount = accountRepository.findById(senderId).get();
		Account receiverAccount = accountRepository.findById(receiverId).get();
		
		String senderName = senderAccount.getUsername();
		String receiverName = receiverAccount.getUsername();
		if (Double.valueOf(senderAccount.getBalance().toString()) < Double.valueOf(amount.toString())) {
			throw new InsufficientAmountException();
		}
		BigDecimal senderNewAmount = senderAccount.getBalance().subtract(amount);
		BigDecimal receiverNewAmount = receiverAccount.getBalance().add(amount);
		
		accountRepository.updateBalance(senderId, senderNewAmount);
		accountRepository.updateBalance(receiverId, receiverNewAmount);
		 
		BigDecimal senderBalance = accountRepository.findById(senderId).get().getBalance();
		BigDecimal receiverBalance = accountRepository.findById(receiverId).get().getBalance();
		
//		balanceWebSocketHandler.sendToUser(senderName, receiverName, senderBalance.subtract(amount), receiverBalance.add(amount));
		 
	}
	
	public void getUpdatedBalance(HttpSession session) throws UserNotFoundException {
		long id = (long) session.getAttribute("id");
		BigDecimal balance = accountRepository.findById(id).get().getBalance();
		
		// update the front-end of the changes
//		balanceWebSocketHandler.sendBalanceUpdate(balance);

		
		logger.info("here, just testing, the new Balnce "+ balance );
		
		
	}
}
