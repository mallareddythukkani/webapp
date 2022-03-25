package com.parkingservice.webapp.controller;

import static java.lang.System.out;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.SAXException;

import com.parkingservice.webapp.model.ExitGate;
import com.parkingservice.webapp.model.Role;
import com.parkingservice.webapp.model.User;
import com.parkingservice.webapp.model.UserEmployee;
import com.parkingservice.webapp.service.MasterService;
import com.parkingservice.webapp.util.Helper;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private MasterService masterService;
	
	@RequestMapping("/list")
	public String userList(Model theModel, HttpServletRequest request) {
		HttpSession session = request.getSession();
		int roleId = (Integer) session.getAttribute("role_id");
		int[] roleList = Helper.getAllowedRoleStr(roleId);
		List<User> records = masterService.getUserList(roleList);
		List<Role> allowedRoles = masterService.getAllowedRoleList(roleId);
		theModel.addAttribute("records",records);
		theModel.addAttribute("allowedRoles",allowedRoles);
		return "masters/user";
	}
	
	@GetMapping("/add")
	public String addUser(Model theModel, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = new User();
		Integer locationId = (Integer) request.getSession().getAttribute("location_id");
		if(locationId==null)
			locationId = 1;
		user.setLocationId(locationId);
		int roleId = (Integer) session.getAttribute("role_id");
		List<Role> allowedRoles = masterService.getAllowedRoleList(roleId);
		List<ExitGate> exitGateList = masterService.getExitGateList(locationId);
		theModel.addAttribute("pageTitle", "Add User");
		theModel.addAttribute("user", user);
		theModel.addAttribute("allowedRoles",allowedRoles);
		theModel.addAttribute("exitGateList",exitGateList);
		return "masters/user-form";
	}
	
	@PostMapping("/save")
	public String saveUser(@ModelAttribute("user") User theUser, HttpServletRequest request)
			throws IOException, TransformerException, ParserConfigurationException, SAXException {
		HttpSession session = request.getSession();
		int loggedInUserId = (Integer) session.getAttribute("user_id");
		if(theUser.getUserId()>0) {
			theUser.setModifiedBy(loggedInUserId);
			theUser.setModifiedTime(Helper.getCurrentDateTime());
		}
		else {
			theUser.setCreatedBy(loggedInUserId);
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			String pwd = (String) request.getParameter("password");
			if(!(pwd.isEmpty()))
				theUser.setPassword(bcrypt.encode(pwd));
		}
		theUser.setStatus(1);
		
		int userId = masterService.save(theUser);
		Integer[] empRoles = new Integer[] {4,5,6};
		List<Integer> empRoleList = Arrays.asList(empRoles);
		//Insert User Employee Details
		int roleId = theUser.getRole().getRoleId();
		if(empRoleList.contains(roleId)) {
			UserEmployee uEmp = null;
			if(theUser.getUserId()>0) {
				uEmp = masterService.findById(userId);
			}
			
			if(uEmp==null){
				uEmp= new UserEmployee();
				uEmp.setUserId(userId);
				uEmp.setShiftId(1); //Set Default Shift
			}
			if(roleId==5) { //SAVE EXIT GATE INFO FOR CASHIER
				String exit_gate = request.getParameter("exit_gate");
				if(!exit_gate.isEmpty()) {
					int exitGateId = Integer.parseInt(exit_gate);
					ExitGate ex = masterService.getExitGate(exitGateId);
					uEmp.setExitGate(ex);
				}
			}
			masterService.save(uEmp);
		}
		
		if(userId>0) {
			request.getSession().setAttribute("flashMessage","<div class=\"alert alert-success alert-dismissible\" role=\"alert\">\r\n" + 
					"			  <strong>Success!</strong> User details saved successfully.\r\n" + 
					"			</div>");
		
		}
		else {
			request.getSession().setAttribute("flashMessage","<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">\r\n" + 
					"			  <strong>Error!</strong> Something went wrong.\r\n" + 
					"			</div>");
		}
		return "redirect:/user/list";
	}
	
	@GetMapping("/edit/{Id}")
	public String editUser(@PathVariable(value="Id") int userId, Model theModel, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = masterService.findUserById(userId);
		Integer locationId = (Integer) request.getSession().getAttribute("location_id");
		int roleId = (Integer) session.getAttribute("role_id");
		List<Role> allowedRoles = masterService.getAllowedRoleList(roleId);
		List<ExitGate> exitGateList = masterService.getExitGateList(locationId);
		int userRoleId = 0;
		  if(user.getUserId()>0){
			  try{
				  userRoleId = user.getRole().getRoleId();
				  
			  }
			  catch(Exception e){ }
		  }
		int[] roleList = Helper.getAllowedRoleStr(roleId);
		if(!Helper.checkArrayContainsElement(roleList, userRoleId)) {
			return "error/unauthorized";
		}
		int userExitGateId = 0;
		UserEmployee userEmp = masterService.findById(userId);
		if(userEmp!=null) {
			try {
				userExitGateId = userEmp.getExitGate().getExitGateId();
			}
			catch(Exception e) { }
		}
		theModel.addAttribute("userRoleId", userRoleId);
		theModel.addAttribute("userExitGateId", userExitGateId);
		theModel.addAttribute("pageTitle", "Edit User");
		theModel.addAttribute("user", user);
		theModel.addAttribute("allowedRoles",allowedRoles);
		theModel.addAttribute("exitGateList",exitGateList);
		return "masters/user-form";
	}
	
	@PostMapping("/deactivate/{Id}")
	public String deactivateUser(@PathVariable(value="Id") int userId, Model theModel, HttpServletRequest request) {
		HttpSession session = request.getSession();
		int loggedInUserId = (Integer) session.getAttribute("user_id");
		User user = masterService.findUserById(userId);
		user.setStatus(2);
		user.setModifiedBy(loggedInUserId);
		user.setModifiedTime(Helper.getCurrentDateTime());
		masterService.save(user);
		request.getSession().setAttribute("flashMessage","<div class=\"alert alert-success alert-dismissible\" role=\"alert\">\r\n" + 
				"			  <strong>Success!</strong> User deactivated successfully.\r\n" + 
				"			</div>");
		return "redirect:/user/list";
	}
	
	@PostMapping("/activate/{Id}")
	public String activateUser(@PathVariable(value="Id") int userId, Model theModel, HttpServletRequest request) {
		HttpSession session = request.getSession();
		int loggedInUserId = (Integer) session.getAttribute("user_id");
		User user = masterService.findUserById(userId);
		user.setStatus(1);
		user.setModifiedBy(loggedInUserId);
		user.setModifiedTime(Helper.getCurrentDateTime());
		masterService.save(user);
		request.getSession().setAttribute("flashMessage","<div class=\"alert alert-success alert-dismissible\" role=\"alert\">\r\n" + 
				"			  <strong>Success!</strong> User activated successfully.\r\n" + 
				"			</div>");
		return "redirect:/user/list";
	}
	
	@GetMapping("/resetPassword/{Id}")
	public String resetPassword(@PathVariable(value="Id") int userId, Model theModel, HttpServletRequest request) {
		User user = masterService.findUserById(userId);
		theModel.addAttribute("pageTitle", "Reset Password");
		theModel.addAttribute("user", user);
		return "masters/reset-password";
	}
	
	@PostMapping("/updatePassword")
	public String updatePassword(HttpServletRequest request)
			throws IOException, TransformerException, ParserConfigurationException, SAXException {
		HttpSession session = request.getSession();
		int loggedInUserId = (Integer) session.getAttribute("user_id");
		int userId = Integer.parseInt(request.getParameter("userId"));
		User user = masterService.findUserById(userId);
		if(user!=null) {
			user.setModifiedBy(loggedInUserId);
			user.setModifiedTime(Helper.getCurrentDateTime());
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			String pwd = (String) request.getParameter("password");
			if(!(pwd.isEmpty()))
				user.setPassword(bcrypt.encode(pwd));
			
			request.getSession().setAttribute("flashMessage","<div class=\"alert alert-success alert-dismissible\" role=\"alert\">\r\n" + 
					"			  <strong>Success!</strong> User password has been resetted successfully.\r\n" + 
					"			</div>");
		}
		else {
			
			request.getSession().setAttribute("flashMessage","<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">\r\n" + 
					"			  <strong>Error!</strong> Something went wrong.\r\n" + 
					"			</div>");
		}
		masterService.save(user);
		return "redirect:/user/list";
	}
}
