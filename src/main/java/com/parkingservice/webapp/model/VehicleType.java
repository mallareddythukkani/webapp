package com.parkingservice.webapp.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the vehicle_type database table.
 * 
 */
@Entity
@Table(name="vehicle_type")
@NamedQuery(name="VehicleType.findAll", query="SELECT v FROM VehicleType v")
public class VehicleType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="vehicle_type_id")
	private short vehicleTypeId;

	private String name;

	private short status;

	

	public VehicleType() {
	}

	public short getVehicleTypeId() {
		return this.vehicleTypeId;
	}

	public void setVehicleTypeId(short vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
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

}