package com.parkingservice.webapp.config;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.parkingservice.webapp.model.UserLog;
import com.parkingservice.webapp.repository.UserLogRepository;
import com.parkingservice.webapp.util.Helper;

@Service
public class CustomLogoutHandler implements LogoutHandler {
 
   @Autowired
   UserLogRepository userLogRepository;
   
   @Override
	public void logout(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.Authentication authentication) {
	   int userLogId = 0;
	   try {
	    userLogId = (Integer) request.getSession().getAttribute("user_log_id");
	   }
	   catch(Exception e) {}
	   if(userLogId>0) {
		   UserLog log = userLogRepository.findById(userLogId).orElse(null);
		   if(log!=null) {
			   log.setLogoutTime(Helper.getCurrentDateTime());
			   userLogRepository.save(log);
		   }
	   }
	}
}
