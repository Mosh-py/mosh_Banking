package com.moshood.config;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.moshood.exception.UserNotFoundException;
import com.moshood.observer.LogInComponent;
import com.moshood.repository.AccountRepository;
import com.moshood.services.SessionService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Service
public class BalanceWebSocketHandler extends TextWebSocketHandler {

	private static Map<String, WebSocketSession> sessions = new ConcurrentHashMap<String, WebSocketSession>();
	private final Logger logger = Logger.getLogger(" from the hsmdler");

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		String username = (String) session.getAttributes().get("username");
		if (username != null) {
			sessions.put(username.toLowerCase(), session);
			logger.info("Session inputted succesfully" + username);
		} else {
			logger.info(" webSocket connected without username");
		}
		logger.info("" + sessions.size());
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// Handle incoming messages if needed
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String username = (String) session.getAttributes().get("username");
		sessions.remove(username);
		logger.info(username +" has been removed");
		logger.info("The session size is " + sessions.size());
	}
//	// method that sends the balance automatically using their unique username
	public void sendBalanceUpdate(String id, BigDecimal balance) throws UserNotFoundException {
		try {
			logger.info("The number of session is " + sessions.size());
			logger.info("the keys are " + sessions.keySet());
			logger.info(" " + sessions.containsKey(id));
			logger.info(" The id we are lokin for " + id);
			WebSocketSession session = sessions.get(id);
			logger.info("Gotten two sessions " + id + session);

			if (session != null && session.isOpen()) {
				logger.info("Trying to send to frontend");
				String message = "{\"balance\": " + balance + "}";
				try {
					session.sendMessage(new TextMessage(message));
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
	
	
	public void sendToUser( String receiverId,BigDecimal receiverBalance, String senderId, BigDecimal senderBalance)
			throws UserNotFoundException { 

		sendBalanceUpdate(receiverId, receiverBalance);
		sendBalanceUpdate(senderId, senderBalance); 
		
	}
}
