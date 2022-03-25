package com.parkingservice.webapp.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.parkingservice.webapp.model.ExitGate;
import com.parkingservice.webapp.model.MyUserDetails;
import com.parkingservice.webapp.model.User;
import com.parkingservice.webapp.model.UserEmployee;
import com.parkingservice.webapp.model.UserLog;
import com.parkingservice.webapp.service.MasterService;
import com.parkingservice.webapp.util.Helper;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private MasterService masterService;
	@Autowired
	private CustomLogoutHandler logoutHandler;
	@Value("${sitename}")
    private String sitename;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/").hasAnyRole()
			.antMatchers("/exit/**").hasRole("CASHIER")
			.antMatchers("/report/**").hasAnyRole("SUPERVISER","MANAGER","BRANCH HEAD")
			.and()
			.formLogin().loginPage("/login")
			.successHandler(new AuthenticationSuccessHandler() {
			     
			    @Override
			    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			            Authentication authentication) throws IOException, ServletException {
			         
			        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
			        User user = userDetails.getUser();
			        //adding session
			        HttpSession session = request.getSession(false);
			        int role_id = user.getRole().getRoleId();
			        session.setAttribute("user_id", user.getUserId());
			        session.setAttribute("role_id", role_id);
			        session.setAttribute("name", user.getName());
			        session.setAttribute("location_id", user.getLocationId());
			        session.setAttribute("flashMessage", "");
			        session.setAttribute("sitename", sitename);
			        if(role_id==5) { //CASHIER
			        	//GET EXIT GATE DETAILS
			        	UserEmployee userEmp = masterService.findById(user.getUserId());
			        	if(userEmp!=null) {
			        		ExitGate exitGate = userEmp.getExitGate();
			        		session.setAttribute("exit_gate_id", exitGate.getExitGateId());
			        		session.setAttribute("exit_gate_name", exitGate.getName());
			        		short vtId = exitGate.getVehicleType().getVehicleTypeId();
			        		int vehicleTypeId = new Short(vtId).intValue();
			        		session.setAttribute("vehicle_type_id", vehicleTypeId);
			        	}
			        }
			        //Insert UserLog
			        UserLog userLog = new UserLog();
			        userLog.setUserId(user.getUserId());
			        userLog.setDeviceTypeId(Helper.deviceTypeId);
			        userLog.setLoginTime(Helper.getCurrentDateTime());
			        userLog.setLastActive(Helper.getCurrentDateTime());
			        userLog.setIpAddress(Helper.getClientIpAddress(request));
			        userLog.setUserAgent(request.getHeader("User-Agent"));
			        int userLogId = masterService.save(userLog);
			        session.setAttribute("user_log_id", userLogId);
			        
			        response.sendRedirect("home");
			    }
			})
			.and().logout()
			.addLogoutHandler(logoutHandler)
			.permitAll();
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		//return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
	}
	
	

}
