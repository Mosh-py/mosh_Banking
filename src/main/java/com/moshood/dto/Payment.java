package com.moshood.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data

public class Payment {

	private String senderName;
	private String receiverName;
	private BigDecimal amount;
	

}
