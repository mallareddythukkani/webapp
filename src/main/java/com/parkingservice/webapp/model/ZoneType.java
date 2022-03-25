package com.parkingservice.webapp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ZoneType {
	@Id
	private int zoneTypeId;
	private String name;
	private int status=1;
	
	public ZoneType() {
		
	}

	public int getZoneTypeId() {
		return zoneTypeId;
	}

	public void setZoneTypeId(int zoneTypeId) {
		this.zoneTypeId = zoneTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
