package com.moshood.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moshood.apiDomain.Article;
import com.moshood.apiDomain.NewsResponse;
import com.moshood.observer.LogInComponent;
import com.moshood.services.BbcNewsService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/app/v1/news")
@RequiredArgsConstructor
public class NewsController {
	private final BbcNewsService newsService;
	private final LogInComponent logInComponent;
	private final Logger logger = Logger.getLogger("News Controller");
	@GetMapping
	public String fetchNewsResponse(Model model, HttpSession session) {
		
		
		if (logInComponent.getUserName() !=null) {
			long id =(long) session.getAttribute("id") ;
			logger.info(" " + id);
			NewsResponse news = newsService.fetchTopHeadlines(id).get();
			if (news != null) {
				List<Article> articles = news.getArticles();
				model.addAttribute("articles", articles);
				return "bbcNewsHandler";
			}
		}
		
		return "login";
	} 
	
} 
