package com.moshood.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.moshood.config.BalanceWebSocketHandler;
import com.moshood.exception.UserNotFoundException;

@Service
public class BalanceService {
	private final BalanceWebSocketHandler handler;
	
	public BalanceService(BalanceWebSocketHandler handler) {
		this.handler = handler;
	}
	
	public void updateBalance(String receiverId, BigDecimal receiverBalance, String senderId, BigDecimal senderBalance) throws UserNotFoundException {
		handler.sendToUser(receiverId, receiverBalance, senderId, senderBalance);	
		
	}
}
