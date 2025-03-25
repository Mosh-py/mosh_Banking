package com.moshood.apiDomain;

import java.util.List;

import lombok.Data;

@Data

public class NewsResponse {
	private String status;
	private int totalResults;
	private List<Article> articles;
}
