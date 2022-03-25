package com.parkingservice.webapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xml.sax.SAXException;

import com.parkingservice.webapp.model.ParkingTariff;
import com.parkingservice.webapp.model.Tenant;
import com.parkingservice.webapp.model.User;
import com.parkingservice.webapp.service.MasterService;

@Controller
public class TariffController {

	private static final Logger log = LoggerFactory.getLogger(TariffController.class);
	
	@Autowired
	private MasterService masterService;
	
	@RequestMapping("/tariff")
	public String getTariff(Model theModel, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer locationId = (Integer) request.getSession().getAttribute("location_id");
		if(locationId==null)
			locationId = 1;
		List<ParkingTariff> pt_4w = masterService.getParkingTariffList(locationId, 1);
		List<ParkingTariff> pt_2w = masterService.getParkingTariffList(locationId, 2);
		theModel.addAttribute("pt_4w", pt_4w);
		theModel.addAttribute("pt_2w", pt_2w);
		theModel.addAttribute("lot_4w", masterService.getPreference(locationId, "general_settings", "4w_lot_charge"));
		theModel.addAttribute("overnt_4w", masterService.getPreference(locationId, "general_settings", "4w_overnight_charge"));
		theModel.addAttribute("lot_2w", masterService.getPreference(locationId, "general_settings", "2w_lot_charge"));
		theModel.addAttribute("overnt_2w", masterService.getPreference(locationId, "general_settings", "2w_overnight_charge"));
		return "masters/tariff";
	}
	
	@GetMapping("/updateTariff/{Id}")
	public String editTariff(@PathVariable(value="Id") int vehicleTypeId, Model theModel, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = new User();
		Integer locationId = (Integer) request.getSession().getAttribute("location_id");
		if(locationId==null)
			locationId = 1;
		List<ParkingTariff> pt_list = masterService.getParkingTariffList(locationId, vehicleTypeId);
		String lot_pref = (vehicleTypeId==1)?"4w_lot_charge":"2w_lot_charge";
		String overnt_pref = (vehicleTypeId==1)?"4w_overnight_charge":"2w_overnight_charge";
		String vehicleTypeName = (vehicleTypeId==1)?"4-Wheeler":"2-Wheeler";
		theModel.addAttribute("vehicleTypeId", vehicleTypeId);
		theModel.addAttribute("vehicleTypeName", vehicleTypeName);
		theModel.addAttribute("pt_list", pt_list);
		theModel.addAttribute("lot", masterService.getPreference(locationId, "general_settings", lot_pref));
		theModel.addAttribute("overnt", masterService.getPreference(locationId, "general_settings", overnt_pref));
		return "masters/update-tariff";
	}
	
	@PostMapping("/submitTariff")
	public String saveTariff( 
			@RequestParam("fromTime[]") List<Integer> fromTimeList,
			@RequestParam("toTime[]") List<Integer> toTimeList,
			@RequestParam("tariff[]") List<Integer> tariffList,
			@RequestParam("repeatMin[]") List<Integer> repeatMinList,
			@RequestParam("repeatTariff[]") List<Integer> repeatTariffList,
			@RequestParam("lot") String lot,
			@RequestParam("overnt") String overnt,
			HttpServletRequest request)
			throws IOException, TransformerException, ParserConfigurationException, SAXException {
		HttpSession session = request.getSession();
		Integer locationId = (Integer) request.getSession().getAttribute("location_id");
		if(locationId==null)
			locationId = 1;
		int vehicleTypeId = Integer.parseInt(request.getParameter("vehicleTypeId"));
		int i=0;
		List<ParkingTariff> ptList = new ArrayList<>();
		for(Integer fromTime : fromTimeList) {
	        ParkingTariff ptRow = new ParkingTariff();
			ptRow.setLocationId(locationId);
			ptRow.setVehicleTypeId(vehicleTypeId);
			ptRow.setFromTime(fromTime);
			ptRow.setToTime(toTimeList.get(i));
			ptRow.setTariff(tariffList.get(i));
			ptRow.setRepeatMinutes(repeatMinList.get(i));
			ptRow.setRepeatTariff(repeatTariffList.get(i));
			ptList.add(ptRow);
			i++;
	    }
		
		boolean res = masterService.updateTariff(locationId, vehicleTypeId, ptList);
		if(res) {
			String lot_pref = (vehicleTypeId==1)?"4w_lot_charge":"2w_lot_charge";
			String overnt_pref = (vehicleTypeId==1)?"4w_overnight_charge":"2w_overnight_charge";
			String section = "general_settings";
			masterService.setPreference(locationId, section, lot_pref, lot);
			masterService.setPreference(locationId, section, overnt_pref, overnt);
			
			request.getSession().setAttribute("flashMessage","<div class=\"alert alert-success alert-dismissible\" role=\"alert\">\r\n" + 
					"			  <strong>Success!</strong> Tariff details saved successfully.\r\n" + 
					"			</div>");
		
		}
		else {
			request.getSession().setAttribute("flashMessage","<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">\r\n" + 
					"			  <strong>Error!</strong> Something went wrong.\r\n" + 
					"			</div>");
		}
		return "redirect:/tariff";
	}
	
}
