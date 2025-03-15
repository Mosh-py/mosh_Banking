package com.moshood.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "UserLogInDto")
public class UserLogInDto {
	@Id
	private String username;
	private String password;
	private String firstName;
	private String lastName;
}
