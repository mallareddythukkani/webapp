package com.parkingservice.webapp.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="parking_transaction")
public class ParkingTransaction {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private long transactionId;
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="vehicle_type_id")
	private VehicleType vehicleType;
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="location_id")
	private ParkingLocation parkingLocation;
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="zone_id")
	private Zone zone = null;
	@Column(name="vehicle_number")
	private String vehicleNumber;
	@Column(name="entry_time")
	private String entryTime;
	@Column(name="exit_time")
	private String exitTime;
	@Column(name="exempted")
	private int exempted = 0;
	@Column(name="payment_method_id")
	private int paymentMethodId = 1;
	@Column(name="amount")
	private int amount = 0;
	@Column(name="amount_collect_by")
	private Integer amountCollectBy = null;
	@Column(name="exit_gate_id")
	private Integer exitGateId = null;
	@Column(name="remarks")
	private String remarks;
	@Column(name="status")
	private int status = 1;
	@Column(name="exempt_reason_id")
	private Integer exemptReasonId=null;
	
	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="transaction_id")
	private TransactionData transactionData;
	
	public ParkingTransaction() {
		
	}
	
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	public ParkingLocation getParkingLocation() {
		return parkingLocation;
	}
	public void setParkingLocation(ParkingLocation parkingLocation) {
		this.parkingLocation = parkingLocation;
	}
	public Zone getZone() {
		return zone;
	}
	public void setZone(Zone zone) {
		this.zone = zone;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}
	public String getExitTime() {
		return exitTime;
	}
	public void setExitTime(String exitTime) {
		this.exitTime = exitTime;
	}
	public int getExempted() {
		return exempted;
	}
	public void setExempted(int exempted) {
		this.exempted = exempted;
	}
	public int getPaymentMethodId() {
		return paymentMethodId;
	}
	public void setPaymentMethodId(int paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getAmountCollectBy() {
		return amountCollectBy;
	}
	public void setAmountCollectBy(int amountCollectBy) {
		this.amountCollectBy = amountCollectBy;
	}
	public int getExitGateId() {
		return exitGateId;
	}
	public void setExitGateId(int exitGateId) {
		this.exitGateId = exitGateId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Integer getExemptReasonId() {
		return exemptReasonId;
	}
	public void setExemptReasonId(Integer exemptReasonId) {
		this.exemptReasonId = exemptReasonId;
	}
	
	

	public TransactionData getTransactionData() {
		return transactionData;
	}

	public void setTransactionData(TransactionData transactionData) {
		this.transactionData = transactionData;
	}

	@Override
	public String toString() {
		return "ParkingTransaction [transactionId=" + transactionId + ", vehicleType=" + vehicleType
				+ ", parkingLocation=" + parkingLocation + ", zone=" + zone + ", vehicleNumber=" + vehicleNumber
				+ ", entryTime=" + entryTime + ", exitTime=" + exitTime + ", exempted=" + exempted
				+ ", paymentMethodId=" + paymentMethodId + ", amount=" + amount + ", amountCollectBy=" + amountCollectBy
				+ ", exitGateId=" + exitGateId + ", remarks=" + remarks + ", status=" + status + ", exemptReasonId="
				+ exemptReasonId + ", transactionData=" + transactionData + "]";
	}


	
	
}
