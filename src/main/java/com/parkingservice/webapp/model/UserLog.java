package com.parkingservice.webapp.model;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the user_log database table.
 * 
 */
@Entity
@Table(name="user_log")
@NamedQuery(name="UserLog.findAll", query="SELECT u FROM UserLog u")
public class UserLog{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_log_id")
	private int userLogId;

	@Column(name="device_type_id")
	private short deviceTypeId;

	@Column(name="ip_address")
	private String ipAddress;

	@Column(name="last_active")
	private String lastActive;

	@Column(name="login_time")
	private String loginTime;

	@Column(name="logout_time")
	private String logoutTime;

	private String udid;

	@Column(name="user_agent")
	private String userAgent;

	@Column(name="user_id")
	private int userId;

	public UserLog() {
	}

	public int getUserLogId() {
		return this.userLogId;
	}

	public void setUserLogId(int userLogId) {
		this.userLogId = userLogId;
	}

	public short getDeviceTypeId() {
		return this.deviceTypeId;
	}

	public void setDeviceTypeId(short deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getLastActive() {
		return this.lastActive;
	}

	public void setLastActive(String lastActive) {
		this.lastActive = lastActive;
	}

	public String getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLogoutTime() {
		return this.logoutTime;
	}

	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getUdid() {
		return this.udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public String getUserAgent() {
		return this.userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}