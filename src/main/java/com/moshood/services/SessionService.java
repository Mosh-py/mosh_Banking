package com.moshood.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;



@Service
public class SessionService {

	private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<String,WebSocketSession>();
	private Logger logger = Logger.getLogger("Session Service");

	public Map<String, WebSocketSession> addSession(String username, WebSocketSession session) {
		sessions.put(username, null); 
		logger.info(" Session with  " + username + " succesfully added ");
		return sessions;
	}
	
	public boolean sessionExists(String id) {
		return sessions.containsKey(id);
	}
	
	public void removeSession(String id) {
		sessions.remove(id);
	} 
	
	public WebSocketSession getSession(String id) {
		return sessions.get(id);
	}
	
	public Map<String , WebSocketSession> getSessions(){
		return this.sessions;

	}
	
	
}
