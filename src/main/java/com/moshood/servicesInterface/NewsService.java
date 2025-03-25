package com.moshood.servicesInterface;

import java.util.Optional;

import com.moshood.apiDomain.NewsResponse;

public interface NewsService {

	public Optional<NewsResponse> fetchTopHeadlines(long id);
}
