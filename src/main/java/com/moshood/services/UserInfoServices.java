package com.moshood.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.moshood.dto.UserLogInDto;
import com.moshood.repository.UserRepository;

@Service
public class UserInfoServices {

	private final UserRepository userRepository;
	
	public UserInfoServices(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public boolean isIdExists(String username) {
		Optional<UserLogInDto> userLogInModel = userRepository.findById(username);
		return userLogInModel.isPresent();
	}
	
	public UserLogInDto getModel(String username) {
		UserLogInDto userLogInModel = userRepository.findById(username).get();
		return userLogInModel;	
	}
	
	public String getModelPassword(String username) {
		UserLogInDto userLogInModel = this.getModel(username);
		return userLogInModel.getPassword();
	}
	
	public String getModelUsername(String username) {
		return this.getModel(username).getUsername();
	}
	
//	public long getId(String username) {
//		Optional<UserLogInDto> userLogInModel = userRepository.findById(username);
//		userLogInModel.get()
//	}
}
