package com.moshood.observer;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@SessionScope
@Component
@Data
public class LogInComponent {
	private String userName;
	

}
 