package com.parkingservice.webapp.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private boolean active;
	private List<GrantedAuthority> authorities;
	private User user;
	
	public MyUserDetails(String username) {
		this.username = username;
	}
	
	public MyUserDetails(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.active = user.getStatus()==1?true:false;
		this.authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_"+user.getRole().getName()));
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		//return "pass";
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return active;
	}

	public User getUser() {
		return user;
	}

	//custom methood
	
}
