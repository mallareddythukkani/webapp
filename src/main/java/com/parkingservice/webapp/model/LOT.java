package com.parkingservice.webapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parkingservice.webapp.util.Helper;

@Entity
public class LOT {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int lotId;
	private int locationId;
	private int vehicleTypeId;
	private String vehicleNumber;
	private int amountCollected;
	private String remarks;
	@JsonIgnore
	private int deviceTypeId = Helper.POS_deviceType;
	private String name;
	private String mobile;
	private String idProof;
	private int createdBy;
	//@JsonProperty(access = Access.READ_ONLY)
	private String createdTime;
	private int exitGateId;
	
	public LOT() {}

	public int getLotId() {
		return lotId;
	}

	public void setLotId(int lotId) {
		this.lotId = lotId;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public int getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(int vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public int getAmountCollected() {
		return amountCollected;
	}

	public void setAmountCollected(int amountCollected) {
		this.amountCollected = amountCollected;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(int deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdProof() {
		return idProof;
	}

	public void setIdProof(String idProof) {
		this.idProof = idProof;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public int getExitGateId() {
		return exitGateId;
	}

	public void setExitGateId(int exitGateId) {
		this.exitGateId = exitGateId;
	}

	@Override
	public String toString() {
		return "LOT [lotId=" + lotId + ", locationId=" + locationId + ", vehicleTypeId=" + vehicleTypeId
				+ ", vehicleNumber=" + vehicleNumber + ", amountCollected=" + amountCollected + ", remarks=" + remarks
				+ ", deviceTypeId=" + deviceTypeId + ", name=" + name + ", mobile=" + mobile + ", idProof=" + idProof
				+ ", createdBy=" + createdBy + ", createdTime=" + createdTime + ", exitGateId=" + exitGateId + "]";
	}
	
	
}
