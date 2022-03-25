package com.parkingservice.webapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/home")
	public String home(HttpSession session, Model theModel) {
		int role_id = 0;
		try {
			role_id = (Integer) session.getAttribute("role_id");
		}
		catch(Exception e) {
			
		}
		theModel.addAttribute("pageTitle","Home");
		theModel.addAttribute("role_id",role_id);
		String page = "home";
		switch(role_id) {
			case 1:
				page = "dashboard/admin";
			break;
			case 2:
				//page = "dashboard/branch_head";
			break;
			case 3:
				page = "dashboard/manager";
			break;
		}
		return page;
	}
}
