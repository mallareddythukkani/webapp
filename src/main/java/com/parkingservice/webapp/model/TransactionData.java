package com.parkingservice.webapp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TransactionData {
	@Id
	private long transactionId;
	private Integer entryGateId = null;
	private int transTypeId = 1;
	private int entryDeviceType = 2; //Handheld
	private Integer exitDeviceType = null;
	private int close_status = 1;
	private int mode = 1;
	private Integer smartCardId = null;
	private String remarks2 = null;
	private String remarks3 = null;
	
	public TransactionData() {
		
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public int getEntryGateId() {
		return entryGateId;
	}

	public void setEntryGateId(int entryGateId) {
		this.entryGateId = entryGateId;
	}

	public int getTransTypeId() {
		return transTypeId;
	}

	public void setTransTypeId(int transTypeId) {
		this.transTypeId = transTypeId;
	}

	public int getEntryDeviceType() {
		return entryDeviceType;
	}

	public void setEntryDeviceType(int entryDeviceType) {
		this.entryDeviceType = entryDeviceType;
	}

	public Integer getExitDeviceType() {
		return exitDeviceType;
	}

	public void setExitDeviceType(Integer exitDeviceType) {
		this.exitDeviceType = exitDeviceType;
	}

	public int getClose_status() {
		return close_status;
	}

	public void setClose_status(int close_status) {
		this.close_status = close_status;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public Integer getSmartCardId() {
		return smartCardId;
	}

	public void setSmartCardId(Integer smartCardId) {
		this.smartCardId = smartCardId;
	}

	public String getRemarks2() {
		return remarks2;
	}

	public void setRemarks2(String remarks2) {
		this.remarks2 = remarks2;
	}

	public String getRemarks3() {
		return remarks3;
	}

	public void setRemarks3(String remarks3) {
		this.remarks3 = remarks3;
	}
	
	
}
