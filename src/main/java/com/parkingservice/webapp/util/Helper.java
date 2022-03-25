package com.parkingservice.webapp.util;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class Helper {
	public static short deviceTypeId=4; //application
	public static int POS_deviceType=3;
	public static int HH_deviceType=2;
	private static String DTFORMAT = "yyyy-MM-dd HH:mm:ss";
	public static int getTimeDiffInMinutes(String entryTime, String exitTime){
		try {
			DateFormat db_df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date d1 = db_df.parse(entryTime);
			Date d2 = db_df.parse(exitTime);
			long diff = d2.getTime() - d1.getTime();
			long diffMinutes = diff / (60 * 1000);
			int min = (int) Math.round(diffMinutes);
			return min;
		}
		catch(ParseException ex) {
			throw new CustomMessageException("Parse Exception "+ ex);
		}
	}

	public static String getDuration(String entryTime, String exitTime){
		int min = getTimeDiffInMinutes(entryTime, exitTime);
		String duration="";
		if(min<60){
			duration = min+"m";
		}
		else{
			int hours = (int)min/60;
			int remain_min = min - (hours*60);
			duration = hours+"h";
			if(remain_min>0){
				duration += " "+remain_min+"m";
			}
		}
		return duration;

	}
	
	public static String getCurrentDateTime() {
		LocalDateTime date = LocalDateTime.now(); // ZoneId.of("GMT+05:30")
		return date.format(DateTimeFormatter.ofPattern(DTFORMAT));
	}
	
	public static String getFormattedTime(String dtFormat) {
		LocalDateTime date = LocalDateTime.now(); // ZoneId.of("GMT+05:30")
		return date.format(DateTimeFormatter.ofPattern(dtFormat));
	}
	
	public static String getClientIpAddress(HttpServletRequest request) {
        String local_ip = "";
    	// local host name and address 
    	try{
	        InetAddress localhost = InetAddress.getLocalHost(); 
	        local_ip = (localhost.getHostAddress()).trim();
    	}
    	catch(Exception e){
    		
    	}
    	return local_ip;
    }
	
	public static String getTransTypeByID(int trans_type_id){
		String trans_type = "";
		switch(trans_type_id){
		case 1:
			trans_type = "General";
			break;
		case 2:
			trans_type = "Preloaded";
			break;
		case 3:
			trans_type = "Pass";
			break;
		case 4:
			trans_type = "Prebooking";
			break;
		case 5:
			trans_type = "goods_pass";
			break;
		}
		return trans_type;
	}
	
	public static int getDaysBetween(String entry_time, String exit_time) {
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dtTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
        int daysBetween=0;
        try {
			Date dateBefore = dtFormat.parse(entry_time);
    	       Date dateAfter = dtFormat.parse(exit_time);
    	       long difference = dateAfter.getTime() - dateBefore.getTime();
    	       daysBetween = (int)(difference / (1000*60*60*24));
    	       Date end_Date = dtTimeFormat.parse(exit_time);
    	       	 String endDateHour = hourFormat.format(end_Date);
    	       int hr = Integer.parseInt(endDateHour);
    	       if(hr<3){
    	    	   daysBetween-=1;
    	       }
    	 } catch (Exception e) {
    	       //e.printStackTrace();
    	 }
        return daysBetween;
	}
	
	public static String getVehicleTypeByID(int vehicle_type_id){
		String vehicle_type = "";
		switch(vehicle_type_id){
		case 1:
			vehicle_type = "4-Wheeler";
			break;
		case 2:
			vehicle_type = "2-Wheeler";
			break;
		}
		return vehicle_type;
	}
	
	public static int[] getAllowedRoleStr(int roleId) {
		int[] roleStr = null;
		switch(roleId) {
		case 1: //Admin
			
			int[] arr1 = {2,3,4,5,6,7};
			roleStr = arr1;
			break;
		case 2: //Branch Head
			int[] arr2 = {3,4,5,6,7};
			roleStr = arr2;
			break;
		case 3: //Manager
			int[] arr3 = {4,5,6,7};
			roleStr = arr3;
			break;
		case 4: //Superviser
			int[] arr4 = {5,6,7};
			roleStr = arr4;
			break;
		}
		return roleStr;
	}
	
	public static String getNextDate(String curDate) {
		String nextDate = "";
		try {
			final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date;
			date = format.parse(curDate);
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			nextDate = format.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return nextDate;
	}
	
	public static boolean checkArrayContainsElement(int[] arr, int val) {
		boolean res = false; 
        for (int element : arr) { 
            if (element == val) { 
            	res = true; 
                break; 
            } 
        } 
		return res;
	}
	
	public static String convertMinToDuration(int min) {
		String duration="";
		if(min<60){
			duration = min+"m";
		}
		else{
			int hours = (int)min/60;
			int remain_min = min - (hours*60);
			duration = hours+"h";
			if(remain_min>0){
				duration += " "+remain_min+"m";
			}
		}
		return duration;
	}
}
