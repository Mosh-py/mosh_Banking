package com.moshood.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.moshood.observer.LogInComponent;
import com.moshood.repository.AccountRepository;
import com.moshood.services.SessionService;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer{
	
	private final AccountRepository accountRepository;
	private final LogInComponent logInComponent;
	private final SessionService sessionService;
	
	public WebSocketConfiguration(AccountRepository accountRepository, LogInComponent logInComponent, SessionService sessionService) {
		this.accountRepository = accountRepository;
		this.logInComponent = logInComponent;
		this.sessionService = sessionService;
	}
	@Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new BalanceWebSocketHandler( ), "/balance-updates")
        		.addInterceptors(new HttpSessionHandshakeInterceptor())
                .setAllowedOrigins("*"); // Allow all origins for testing, restrict in production
    }

}
 