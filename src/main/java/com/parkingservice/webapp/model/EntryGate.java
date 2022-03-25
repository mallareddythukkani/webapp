package com.parkingservice.webapp.model;

public class EntryGate {
	private int entryGateId;
	private String name;
	private Integer vehicleTypeId;
	
	public EntryGate() {}

	
	
	public EntryGate(int entryGateId, String name, Integer vehicleTypeId) {
		this.entryGateId = entryGateId;
		this.name = name;
		this.vehicleTypeId = vehicleTypeId;
	}



	public int getEntryGateId() {
		return entryGateId;
	}

	public void setEntryGateId(int entryGateId) {
		this.entryGateId = entryGateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getVehicleTypeId() {
		return vehicleTypeId;
	}

	public void setVehicleTypeId(Integer vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}
	
	
}
