package com.parkingservice.webapp.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the user_employee database table.
 * 
 */
@Entity
@Table(name="user_employee")
@NamedQuery(name="UserEmployee.findAll", query="SELECT u FROM UserEmployee u")
public class UserEmployee{
	@Id
	private int userId;
	private String address;

	@Column(name="em_contact_name")
	private String emContactName;

	@Column(name="em_contact_num")
	private String emContactNum;

	@Column(name="em_contact_relation")
	private String emContactRelation;

	@Temporal(TemporalType.DATE)
	@Column(name="joining_date")
	private Date joiningDate;

	private String mobile;

	@Temporal(TemporalType.DATE)
	@Column(name="relieving_date")
	private Date relievingDate;



	private Integer ShiftId;

	//bi-directional many-to-one association to ExitGate
	@ManyToOne
	@JoinColumn(name="exit_gate_id")
	private ExitGate exitGate;

	public UserEmployee() {
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmContactName() {
		return this.emContactName;
	}

	public void setEmContactName(String emContactName) {
		this.emContactName = emContactName;
	}

	public String getEmContactNum() {
		return this.emContactNum;
	}

	public void setEmContactNum(String emContactNum) {
		this.emContactNum = emContactNum;
	}

	public String getEmContactRelation() {
		return this.emContactRelation;
	}

	public void setEmContactRelation(String emContactRelation) {
		this.emContactRelation = emContactRelation;
	}

	public Date getJoiningDate() {
		return this.joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getRelievingDate() {
		return this.relievingDate;
	}

	public void setRelievingDate(Date relievingDate) {
		this.relievingDate = relievingDate;
	}

	public Integer getShiftId() {
		return ShiftId;
	}

	public void setShiftId(Integer shiftId) {
		ShiftId = shiftId;
	}

	public ExitGate getExitGate() {
		return this.exitGate;
	}

	public void setExitGate(ExitGate exitGate) {
		this.exitGate = exitGate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	

}