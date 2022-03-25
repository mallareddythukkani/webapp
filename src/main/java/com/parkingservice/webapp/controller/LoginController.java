package com.parkingservice.webapp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	@Value("${sitename}")
    private String sitename;
	
	@RequestMapping("/login")
	public String login(Model theModel) throws IOException {
		theModel.addAttribute("sitename", sitename);
		return "login";
	}
	
	@RequestMapping("/processLogin")
	public String processLogin(HttpServletResponse response) throws IOException {
		
		response.sendRedirect("/tenant/list");
		return null;
	}
}
