package com.parkingservice.webapp.controller;

import java.io.IOException;
import java.util.Enumeration;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.SAXException;

import com.parkingservice.webapp.model.LOT;
import com.parkingservice.webapp.model.ParkingLocation;
import com.parkingservice.webapp.model.ParkingTransaction;
import com.parkingservice.webapp.model.PtExemptData;
import com.parkingservice.webapp.model.Tenant;
import com.parkingservice.webapp.model.TransactionData;
import com.parkingservice.webapp.model.VehicleType;
import com.parkingservice.webapp.service.MasterService;
import com.parkingservice.webapp.service.TransactionService;
import com.parkingservice.webapp.util.Helper;

@Controller
@RequestMapping("/exit")
public class ExitScreenController {

	private static final Logger log = LoggerFactory.getLogger(ExitScreenController.class);
	
	@Autowired
	private MasterService masterService;
	
	@Autowired
	private TransactionService transService;
	
	@RequestMapping("/screen")
	public String exitScreen(Model theModel) {
		return "exit/exit-screen";
	}
	
	@PostMapping("/exitFormSubmit")
	public String exitFormSubmit(HttpServletRequest request) {
		String trans_id = request.getParameter("trans_id");
		String serial_num = request.getParameter("serial_num");
		String error_resp = null;
		ParkingTransaction pt = null;
		if(!trans_id.isEmpty()) {
			String firstChar = trans_id.substring(0,1);
			String pt_id = trans_id.substring(1);
			if(pt_id.length()>12)
				pt_id = trans_id.substring(1,13);
			switch(firstChar.toUpperCase()) {
				case "S":
					pt = transService.findByTransactionId(Long.parseLong(pt_id)) ;
					break;
				case "M": //Manual Ticket
					String timestr = trans_id.substring(1);
					if(timestr.length()==15){
						String timestamp = timestr.substring(0,4)+"-"+timestr.substring(4,6)+"-"+timestr.substring(6,8)+" "+timestr.substring(8,10)+":"+timestr.substring(10,12)+":"+timestr.substring(12,14);
						String vehicle_type = timestr.substring(14);
						int locationId = (int) request.getSession().getAttribute("location_id");
						pt = new ParkingTransaction();
						ParkingLocation pl = transService.findParkingLocationById(locationId);
						pt.setParkingLocation(pl);
						VehicleType vt = transService.findVehicleTypeById(Short.parseShort(vehicle_type));
						pt.setVehicleType(vt);
						pt.setEntryTime(timestamp);
						pt.setStatus(1);
						
						pt = transService.save(pt);
						
						//Save Transaction Data
						TransactionData txRow = new TransactionData();
						txRow.setTransactionId(pt.getTransactionId());
						txRow.setMode(2); //manual
						txRow.setEntryDeviceType(Helper.HH_deviceType);
						transService.save(txRow);
					}
					break;
				default:
					
					break;
			}
		}
		else if(serial_num!=null) {
			pt = transService.findTransactionBySerialNumber(serial_num);
			
		}
		//check it is valid transaction id
		if(pt!=null) {
			TransactionData td = pt.getTransactionData();
			if(pt.getExitTime()!=null && td.getClose_status()==1) { //VEHICLE ALREADY EXITED
				error_resp = "VEHICLE ALREADY EXITED";
			}
		}
		else {
			error_resp = "INVALID TRANSACTION";
		}
		if(error_resp==null&&pt!=null)
			return "redirect:/exit/paymentCalculation?pt_id="+pt.getTransactionId();
		
		request.getSession().setAttribute("flashMessage","<div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\"><strong>Error!</strong> "+error_resp+", Trans ID: "+trans_id+".\r\n" + 
								"<button class=\"close\" type=\"button\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">×</span></button>\r\n" + 
								"</div>");
		return "redirect:/exit/screen?err=1";
	}
	
	@GetMapping("/paymentCalculation")
	public String paymentCalculation(Model theModel, HttpServletRequest request) {
		String pt_id = request.getParameter("pt_id");
		ParkingTransaction pt = transService.findByTransactionId(Long.parseLong(pt_id));
		if(pt==null) {
			request.getSession().setAttribute("flashMessage","<div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\"><strong>Error!</strong> Invalid Transaction, Trans ID: "+pt_id+".\r\n" + 
					"<button class=\"close\" type=\"button\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">×</span></button>\r\n" + 
					"</div>");
			return "redirect:/exit/screen?err=1";
		}
		else {
			
			String entryTime = pt.getEntryTime();
			String exitTime = Helper.getCurrentDateTime();
			int locationId = pt.getParkingLocation().getLocationId();
			int vehicleTypeId = pt.getVehicleType().getVehicleTypeId();
			int amount = transService.getTariffAmount(entryTime, exitTime, locationId, vehicleTypeId);
			String overnight_pref_name = (vehicleTypeId==1)?"4w_overnight_charge":"2w_overnight_charge";
			String overnight_val = transService.getPreference(locationId, "general_settings", overnight_pref_name);
			int overnight_charge = (vehicleTypeId==1)?200:100;
			try {
				if(!overnight_val.isEmpty())
					overnight_charge = Integer.parseInt(overnight_val);
			}
			catch(Exception e2) {
				
			}
			String parkTime = Helper.getDuration(entryTime, exitTime);
			TransactionData td = pt.getTransactionData();
			List<Tenant> records = masterService.getList();
			theModel.addAttribute("tenant_records", records);
			theModel.addAttribute("pt_row", pt);
			theModel.addAttribute("td_row", td);
			theModel.addAttribute("amount", amount);
			theModel.addAttribute("exit_time", exitTime);
			theModel.addAttribute("duration", parkTime);
			theModel.addAttribute("overnight_charge", overnight_charge);
			theModel.addAttribute("trans_type", transService.getTransTypeById(td.getTransTypeId()));
			return "exit/payment-calculation";
		}
	}
	
	@PostMapping("/makePayment")
	public String makePayment(HttpServletRequest request)
			throws IOException, TransformerException, ParserConfigurationException, SAXException {
		try {
			HttpSession session = request.getSession();
//			Enumeration<String> attributes = request.getParameterNames();
//			while(attributes.hasMoreElements()) { String attribute = (String)
//			attributes.nextElement();
//			log.info(attribute+":"+request.getParameter(attribute)); }
			
			long transactionId = Long.parseLong(request.getParameter("transaction_id"));
			String exitTime = request.getParameter("exit_time");
			int amount = Integer.parseInt(request.getParameter("amount"));
			int paymentMethodId = Integer.parseInt(request.getParameter("payment_method"));
			String exempt_payment = request.getParameter("exempt_payment");
			
			int exempted = 0;
			try{
				exempted = (exempt_payment.equals("1"))?1:0;
			}
			catch(Exception e){
				
			}
			String remarks = request.getParameter("remarks");
			int userId = (int) request.getSession(false).getAttribute("user_id");
			int exitGateId = (int) request.getSession(false).getAttribute("exit_gate_id");
			
			ParkingTransaction pt = transService.findByTransactionId(transactionId);
			pt.setExitTime(exitTime);
			pt.setAmount(amount);
			pt.setExempted(exempted);
			pt.setAmountCollectBy(userId);
			pt.setExitGateId(exitGateId);
			pt.setStatus(1);
			remarks = remarks.trim();
			if(remarks!=null&&!remarks.equals(""))
				pt.setRemarks(remarks);
			if(exempted==1) {
				int exemptReasonId = Integer.parseInt(request.getParameter("exempt_reason"));
				pt.setExemptReasonId(exemptReasonId);
				if(exemptReasonId==2) { //shopping
					String tenant_id = request.getParameter("tenant");
					if(tenant_id!=null&&!tenant_id.equals("")) {
						PtExemptData exmpData = new PtExemptData();
						exmpData.setTransactionId(transactionId);
						exmpData.setTenantId(Integer.parseInt(tenant_id));
						String bill_amount = request.getParameter("bill_amount");
						int billAmount = (bill_amount!="")?Integer.parseInt(bill_amount):0;
						exmpData.setBillAmount(billAmount);
						exmpData.setBillNumber(request.getParameter("bill_number"));
						transService.save(exmpData);
					}
				}
			}
			TransactionData td = pt.getTransactionData();
			td.setClose_status(1);
			td.setExitDeviceType(Helper.POS_deviceType);
			pt.setTransactionData(td);
			transService.save(pt);
			
			return "redirect:/exit/print/"+transactionId;
		}
		catch(Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("flashMessage","<div class=\"alert alert-success alert-dismissible\" role=\"alert\">\r\n" + 
					"			  <strong>Error!</strong> Something went wrong.\r\n" + 
					"			</div>");
		}
		//return "";
		return "redirect:/exit/screen";
	}
	
	@GetMapping("/print/{Id}")
	public String printTransaction(@PathVariable(value="Id") long transactionId, Model theModel) {
		ParkingTransaction pt = transService.findByTransactionId(transactionId);
		String parkTime = Helper.getDuration(pt.getEntryTime(), pt.getExitTime());
		theModel.addAttribute("pt_row", pt);
		theModel.addAttribute("duration", parkTime);
		return "exit/print-receipt";
	}
	
	@RequestMapping("/lot")
	public String lotList(Model theModel, HttpServletRequest request) {
		HttpSession session = request.getSession();
		int userId = (Integer) session.getAttribute("user_id");
		List<LOT> records = masterService.getLOTList(userId);
		theModel.addAttribute("records",records);
		return "exit/lot";
	}
	
	@GetMapping("/addLot")
	public String addLot(Model theModel, HttpServletRequest request) {
		HttpSession session = request.getSession();
		LOT lot = new LOT();
		int locationId = (Integer) session.getAttribute("location_id"); 
		lot.setLocationId(locationId);
		int vehicleTypeId = (Integer) session.getAttribute("vehicle_type_id");
		lot.setVehicleTypeId(vehicleTypeId);
		String lot_pref_name = (vehicleTypeId==1)?"4w_lot_charge":"2w_lot_charge";
		String lot_val = transService.getPreference(locationId, "general_settings", lot_pref_name);
		if(!lot_val.isEmpty())
			lot.setAmountCollected(Integer.parseInt(lot_val));
		theModel.addAttribute("lot", lot);
		return "exit/lot-form";
	}
	
	@PostMapping("/saveLot")
	public String saveLot(@ModelAttribute("lot") LOT theLot, HttpServletRequest request)
			throws IOException, TransformerException, ParserConfigurationException, SAXException {
		HttpSession session = request.getSession();
		theLot.setCreatedBy((Integer) session.getAttribute("user_id"));
		theLot.setExitGateId((Integer) session.getAttribute("exit_gate_id"));
		
		int lotId = masterService.save(theLot);
		log.debug("lotId", lotId);
		if(lotId>0) {
			return "redirect:/exit/printLot/"+lotId;
		}
		else {
			request.getSession().setAttribute("flashMessage","<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">\r\n" + 
					"			  <strong>Error!</strong> Something went wrong.\r\n" + 
					"			</div>");
		}
		return "redirect:/exit/lot";
	}
	
	@GetMapping("/printLot/{Id}")
	public String printLot(@PathVariable(value="Id") int lotId, Model theModel) {
		LOT lot  = masterService.findByLotId(lotId);
		theModel.addAttribute("lot_row", lot);
		theModel.addAttribute("vehicleType", Helper.getVehicleTypeByID(lot.getVehicleTypeId()));
		return "exit/print-lot-receipt";
	}
}
