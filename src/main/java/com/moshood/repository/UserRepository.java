package com.moshood.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moshood.dto.UserLogInDto;

public interface UserRepository extends JpaRepository<UserLogInDto, String>{
 
	

}
