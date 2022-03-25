package com.parkingservice.webapp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PtExemptData {
	@Id
	private long transactionId;
	private int tenantId;
	private String billNumber;
	private int billAmount;
	
	public PtExemptData() {}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public int getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(int billAmount) {
		this.billAmount = billAmount;
	}
	
	
}
