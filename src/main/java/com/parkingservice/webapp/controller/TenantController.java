package com.parkingservice.webapp.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xml.sax.SAXException;

import com.parkingservice.webapp.model.Tenant;
import com.parkingservice.webapp.service.MasterService;

@Controller
@RequestMapping("/tenant")
public class TenantController {

	private static final Logger log = LoggerFactory.getLogger(TenantController.class);
	
	@Autowired
	private MasterService masterService;
	
	@RequestMapping("/list")
	public String tenantList(Model theModel) {
		List<Tenant> records = masterService.getList();
		theModel.addAttribute("pageTitle","Tenant List");
		theModel.addAttribute("records",records);
		return "masters/tenant";
	}
	
	@GetMapping("/add")
	public String showFormForAdd(Model theModel, HttpServletRequest request) {
		log.debug("inside show mian-form handler method");
		Tenant tenant = new Tenant();
		tenant.setLocationId(1);
		theModel.addAttribute("tenant", tenant);
		return "masters/tenant-form";
	}
	
	@PostMapping("/save")
	public String saveMainReport(@ModelAttribute("tenant") Tenant theTenant, HttpServletRequest request)
			throws IOException, TransformerException, ParserConfigurationException, SAXException {
		HttpSession session = request.getSession();
		if(theTenant.getTenantId()>0) {
			theTenant.setModifiedBy((Integer) session.getAttribute("user_id"));
		}
		int tenantId = masterService.save(theTenant);
		log.debug("tenantId", tenantId);
		if(tenantId>0) {
			request.getSession().setAttribute("flashMessage","<div class=\"alert alert-success alert-dismissible\" role=\"alert\">\r\n" + 
					"			  <strong>Success!</strong> Tenant details saved successfully.\r\n" + 
					"			</div>");
		
		}
		else {
			request.getSession().setAttribute("flashMessage","<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">\r\n" + 
					"			  <strong>Error!</strong> Something went wrong.\r\n" + 
					"			</div>");
		}
		return "redirect:/tenant/list";
	}
	
	@GetMapping("/edit/{Id}")
	public String showFormForUpdate(@PathVariable(value="Id") int tenantId, Model theModel) {
		Tenant theTenant = masterService.get(tenantId);
		theModel.addAttribute("tenant", theTenant);
		return "masters/tenant-form";
	}
}
