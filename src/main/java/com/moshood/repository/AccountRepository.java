package com.moshood.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.moshood.exception.UserNotFoundException;
import com.moshood.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
	@Modifying
	@Query("Update Account Set balance = :balance where id = :id ")
	void updateBalance(long id, BigDecimal balance);
	
	
	Account findByUsername(String userName);
	
	Optional<Account> findById(long id) throws UserNotFoundException;
}
