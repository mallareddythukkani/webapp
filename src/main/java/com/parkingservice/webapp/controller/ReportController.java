package com.parkingservice.webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.parkingservice.webapp.bean.DailyReport;
import com.parkingservice.webapp.model.ParkingTransaction;
import com.parkingservice.webapp.model.User;
import com.parkingservice.webapp.model.VehicleType;
import com.parkingservice.webapp.service.MasterService;
import com.parkingservice.webapp.service.ReportService;
import com.parkingservice.webapp.util.Helper;

@Controller
@RequestMapping("/report")
public class ReportController {

	private static final Logger log = LoggerFactory.getLogger(TenantController.class);
	
	@Autowired
	private ReportService reportService;
	@Autowired
	private MasterService masterService;
	
	@RequestMapping("/dailyReport")
	public String dailyReport(Model theModel, HttpServletRequest request) {
		HttpSession session = request.getSession();
		theModel.addAttribute("pageTitle","Daily Report");
		int locationId = (Integer) session.getAttribute("location_id");
		String ondate = request.getParameter("ondate");
		String rSubmit = request.getParameter("rSubmit");
		String viewFile = "reports/daily-report";
		if(rSubmit != null) {
			if(rSubmit.equals("2")) {
				viewFile = "reports/daily-report-print";
			}
		}
		if(ondate==null||ondate.equals("")) {
			ondate = Helper.getFormattedTime("yyyy-MM-dd");
		}
		Map<String,DailyReport> dailyReportData = reportService.getDailyReportData(ondate, locationId);
		theModel.addAttribute("ondate",ondate);
		theModel.addAttribute("4wData",dailyReportData.get("4w"));
		theModel.addAttribute("2wData",dailyReportData.get("2w"));
		theModel.addAttribute("reportTime",Helper.getCurrentDateTime());
		return viewFile;
	}
	
	@RequestMapping("/transactionList")
	
	public String transactionList(Model theModel,HttpServletRequest request) {
		int userId = (int) request.getSession(false).getAttribute("user_id");
		List<ParkingTransaction> ptList = reportService.getPOSTransactionList(userId);
		theModel.addAttribute("ptList",ptList);
		return "reports/transaction-list";
	}
	
	@RequestMapping("/cashierReport")
	public String cashierReport(Model theModel, HttpServletRequest request) {
		HttpSession session = request.getSession();
		theModel.addAttribute("pageTitle","Cashier Report");
		int locationId = (Integer) session.getAttribute("location_id");
		String ondate = request.getParameter("ondate");
		String from_time = request.getParameter("from_time");
		String to_time = request.getParameter("to_time");
		String cashier = request.getParameter("cashier");
		String rSubmit = request.getParameter("rSubmit");
		
		int cashierId = 0;
		String cashierName = "";
		if(ondate==null||ondate.equals("")) {
			ondate = Helper.getFormattedTime("yyyy-MM-dd");
		}
		if(cashier!=null) {
			cashierId = Integer.parseInt(cashier);
			User cuser = masterService.findUserById(cashierId);
			cashierName = cuser.getName();
		}
		List<User> cashierList = masterService.getCashierList(locationId);
		Map<Integer,Integer> paymentRes = null;
		Map<Integer,Integer> lotRes = null;
		Map<String,Integer> exemptRes = null;
		String viewFile = "reports/cashier-report";
		if(rSubmit!=null) {
			String nextdate = Helper.getNextDate(ondate);
			String fromTime; 
			String toTime;
			if(!from_time.equals("") && !to_time.equals("")) {
				fromTime = ondate+" "+from_time+":00";
				toTime = ondate+" "+to_time+":00";
			}
			else {
				fromTime = ondate+" 03:00:00";
				toTime = nextdate+" 03:00:00";
			}
			paymentRes = reportService.getCashierPaymentStats(cashierId, fromTime, toTime);
			lotRes = reportService.getCashierLotStats(cashierId, fromTime, toTime);
			exemptRes = reportService.getCashierExemptStats(cashierId, fromTime, toTime);
			if(rSubmit.equals("2")) {
				viewFile = "reports/cashier-report-print";
			}
			
		}
		theModel.addAttribute("rSubmit",rSubmit);
		theModel.addAttribute("ondate",ondate);
		theModel.addAttribute("cashierList",cashierList);
		theModel.addAttribute("cashierId",cashierId);
		theModel.addAttribute("cashierName",cashierName);
		theModel.addAttribute("from_time",from_time);
		theModel.addAttribute("to_time",to_time);
		theModel.addAttribute("paymentRes",paymentRes);
		theModel.addAttribute("lotRes",lotRes);
		theModel.addAttribute("exemptRes",exemptRes);
		theModel.addAttribute("reportTime",Helper.getCurrentDateTime());
		return viewFile;
	}
	
	@RequestMapping("/transactionReport")
	public String transactionReport(Model theModel, HttpServletRequest request) {
		HttpSession session = request.getSession();
		theModel.addAttribute("pageTitle","Transaction Report");
		int locationId = (Integer) session.getAttribute("location_id");
		String ondate = request.getParameter("ondate");
		String from_time = request.getParameter("from_time");
		String to_time = request.getParameter("to_time");
		String vehicleType = request.getParameter("vehicleTypeId");
		String rSubmit = request.getParameter("rSubmit");
		
		int vehicleTypeId = 0;
		if(ondate==null||ondate.equals("")) {
			ondate = Helper.getFormattedTime("yyyy-MM-dd");
		}
		if(vehicleType!=null && !vehicleType.isEmpty()) {
			vehicleTypeId = Integer.parseInt(vehicleType);
		}
		List<VehicleType> vehicleTypeList = masterService.getVehicleTypeList();
		String viewFile = "reports/transaction-report";
		List<ParkingTransaction> ptList = new ArrayList<>();
		if(rSubmit!=null) {
			String nextdate = Helper.getNextDate(ondate);
			String fromTime; 
			String toTime;
			if(!from_time.equals("") && !to_time.equals("")) {
				fromTime = ondate+" "+from_time+":00";
				toTime = ondate+" "+to_time+":00";
			}
			else {
				fromTime = ondate+" 03:00:00";
				toTime = nextdate+" 03:00:00";
			}
			Map<String,String> searchParams = new HashMap<>();
			searchParams.put("fromTime",fromTime);
			searchParams.put("toTime", toTime);
			searchParams.put("vehicleTypeId", vehicleType);
			ptList = reportService.getTransactionList(searchParams);
			
			if(rSubmit.equals("2")) {
				viewFile = "reports/transaction-report-print";
			}
			
		}
		theModel.addAttribute("ptList",ptList);
		theModel.addAttribute("rSubmit",rSubmit);
		theModel.addAttribute("ondate",ondate);
		theModel.addAttribute("vehicleTypeList",vehicleTypeList);
		theModel.addAttribute("vehicleTypeId",vehicleTypeId);
		theModel.addAttribute("from_time",from_time);
		theModel.addAttribute("to_time",to_time);
		theModel.addAttribute("reportTime",Helper.getCurrentDateTime());
		return viewFile;
	}

	@RequestMapping("/datewiseReport")
	public String datewiseReport(Model theModel, HttpServletRequest request) {
		HttpSession session = request.getSession();
		theModel.addAttribute("pageTitle","Date Wise Report");
		int locationId = (Integer) session.getAttribute("location_id");
		String rSubmit = request.getParameter("rSubmit");
		String viewFile = "reports/datewise-report";
		if(rSubmit != null) {
			String fromDate = request.getParameter("fromdate");
			String toDate = request.getParameter("todate");
			Map<String,DailyReport> dailyReportData = reportService.getDatewiseReportData(fromDate, toDate, locationId);
			theModel.addAttribute("4wData",dailyReportData.get("4w"));
			theModel.addAttribute("2wData",dailyReportData.get("2w"));
			if(rSubmit.equals("2")) {
				viewFile = "reports/datewise-report-print";
				theModel.addAttribute("reportTime",Helper.getCurrentDateTime());
			}
			theModel.addAttribute("fromDate",fromDate);
			theModel.addAttribute("toDate",toDate);
		}
		
		theModel.addAttribute("rSubmit",rSubmit);
		return viewFile;
	}
	
}
