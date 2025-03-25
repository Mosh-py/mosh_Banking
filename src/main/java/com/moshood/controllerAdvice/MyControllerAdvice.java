package com.moshood.controllerAdvice;

import java.net.ConnectException;
import java.net.UnknownHostException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class MyControllerAdvice {

	@ExceptionHandler(UnknownHostException.class)
	public String getunKnownHostException() {
		return "handleError";
	}
	
	@ExceptionHandler(Exception.class)
	public String getException() {
		return "handleError";
	}
	
	@ExceptionHandler(NoResourceFoundException.class)
	public String returnHomePage() {
		return "mainPage";
	}
	
	@ExceptionHandler(ConnectException.class)
	public String checkInternetConnection(HttpSession session) {
		String message = " Timed out, kindly check out your internet connection";
		session.setAttribute("message", message);
		
		return "handleError";
		
	}
}
