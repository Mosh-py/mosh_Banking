package com.moshood.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
//@RequestMapping("/error")
public class CustomErrorController implements ErrorController {
//
//	@GetMapping
//	public String controlError(HttpSession session, Model model) {
//		System.out.println(session.getId());
//		// check if session Exists
//		System.out.println(session.isNew());
//		System.out.println(session.getAttribute("username"));
//		if(session.isNew()) {
//			String message = " Error!!!, something went Wrong kindly "
//					+ "check your internet connection";
//			session.setAttribute("message", message);
//			return "handleError";
//		}
//		;
//		return "handleError";
//	}
}
