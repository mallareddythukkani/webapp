package com.parkingservice.webapp.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the exit_gate database table.
 * 
 */
@Entity
@Table(name="exit_gate")
@NamedQuery(name="ExitGate.findAll", query="SELECT e FROM ExitGate e")
public class ExitGate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="exit_gate_id")
	private int exitGateId;

	@Column(name="created_by")
	private int createdBy;

	@Column(name="created_time")
	private Timestamp createdTime;

	@Column(name="device_id")
	private String deviceId;

	@Column(name="modified_by")
	private Integer modifiedBy;

	@Column(name="modified_time")
	private Timestamp modifiedTime;

	private String name;

	private short status;

	//bi-directional many-to-one association to ParkingLocation
	@ManyToOne
	@JoinColumn(name="location_id")
	private ParkingLocation parkingLocation;

	//bi-directional many-to-one association to VehicleType
	@ManyToOne
	@JoinColumn(name="vehicle_type_id")
	private VehicleType vehicleType;

	public ExitGate() {
	}

	public int getExitGateId() {
		return this.exitGateId;
	}

	public void setExitGateId(int exitGateId) {
		this.exitGateId = exitGateId;
	}

	public int getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedTime() {
		return this.modifiedTime;
	}

	public void setModifiedTime(Timestamp modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public ParkingLocation getParkingLocation() {
		return this.parkingLocation;
	}

	public void setParkingLocation(ParkingLocation parkingLocation) {
		this.parkingLocation = parkingLocation;
	}

	public VehicleType getVehicleType() {
		return this.vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}



}