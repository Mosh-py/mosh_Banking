package com.moshood.controller;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.moshood.dto.UserLogInDto;
import com.moshood.exception.UserNotFoundException;
import com.moshood.model.Account;
import com.moshood.observer.LogInComponent;
import com.moshood.repository.AccountRepository;
import com.moshood.repository.UserRepository;
import com.moshood.services.AccountService;
import com.moshood.services.SessionService;
import com.moshood.services.UserInfoServices;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class WelcomeController {
	private final Logger logger = Logger.getLogger("Yes, my account");
	private final AccountRepository accountRepository;
	private final UserRepository userRepository;
	private final UserInfoServices userInfoServices;
	private final LogInComponent logInComponent;
	private final AccountService accountService;
	

	public WelcomeController(AccountRepository accountRepository, 
			UserRepository userRepository, UserInfoServices userInfoServices,
			LogInComponent logInComponent, AccountService accountService) {
		this.accountRepository = accountRepository;
		this.userRepository = userRepository;
		this.userInfoServices = userInfoServices;
		this.logInComponent = logInComponent;
		this.accountService = accountService;
		
	}

	@GetMapping
	public String getWelcomePage() {
		return "welcome";
	}

	@GetMapping("/signup")
	public String getSignUpPage() {
		return "signup";
	}

	@GetMapping("/login") 
	public String getLogInPage() {
		return "login.html";
	}
	
	@PostMapping("/login")
	public String logInPost(@RequestParam String username, 
			@RequestParam String password, Model model, HttpSession session)  {
		String message = "You inputed the wrong Username";
		
		// get the id of the user and bind it to the session
		try {
			long id = accountService.getAccount(username).getId();
			session.setAttribute("id", id);
		} catch(Exception e) {
			message = " User does'nt exist";
		}
		
		
		
		// check if ID exists
		if (userInfoServices.isIdExists(username)) {
			 String correctPassword = userInfoServices.getModelPassword(username);
			 session.setAttribute("username", username.toLowerCase());	
			 logger.info(correctPassword + " is equal to " + password);
			 logger.info(" " + correctPassword.equals(password));
			 
			 if (correctPassword.equals(password)) {
				 logInComponent.setUserName(username);
				 return "redirect:mainPage";
			 }
			 
			 else {
				 message = "You inputted the wrong Password";
				 model.addAttribute("message", message);
				 return "login";
			 }
		 }
		model.addAttribute("message", message);
		// return logIn.html
		return "login";
	}
	
	

	@PostMapping("/signup")
	public String signUpPost(@RequestParam String username, @RequestParam String password,
			@RequestParam String firstName, @RequestParam String lastName, Model model) {
		UserLogInDto logInModel = new UserLogInDto();
		logInModel.setUsername(username);
		logInModel.setPassword(password);
		logInModel.setFirstName(firstName);
		logInModel.setLastName(lastName);
		
		String message = " successfully signed up";
		
		
		if (userInfoServices.isIdExists(username)) {
			message = " Try another username as username already exists";
			model.addAttribute("message", message);
			return "signup";
		}
		model.addAttribute("message", message);
		
		userRepository.save(logInModel);
		long id = accountService.createAccount(username);
		model.addAttribute("id",id);
		model.addAttribute("id-message", "Your id id "+ id + "kindly keep it well with you");
		return "redirect:login";	
		}
	

}
