package com.parkingservice.webapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ParkingTariff {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int parkingTariffId;
	private int locationId;
	private int vehicleTypeId;
	private int fromTime;
	private int toTime;
	private int tariff;
	private Integer repeatMinutes;
	private Integer repeatTariff;
	
	public ParkingTariff() {}

	public int getParkingTariffId() {
		return parkingTariffId;
	}

	public void setParkingTariffId(int parkingTariffId) {
		this.parkingTariffId = parkingTariffId;
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

	public int getFromTime() {
		return fromTime;
	}

	public void setFromTime(int fromTime) {
		this.fromTime = fromTime;
	}

	public int getToTime() {
		return toTime;
	}

	public void setToTime(int toTime) {
		this.toTime = toTime;
	}

	public int getTariff() {
		return tariff;
	}

	public void setTariff(int tariff) {
		this.tariff = tariff;
	}

	public Integer getRepeatMinutes() {
		return repeatMinutes;
	}

	public void setRepeatMinutes(Integer repeatMinutes) {
		this.repeatMinutes = repeatMinutes;
	}

	public Integer getRepeatTariff() {
		return repeatTariff;
	}

	public void setRepeatTariff(Integer repeatTariff) {
		this.repeatTariff = repeatTariff;
	}

	@Override
	public String toString() {
		return "ParkingTariff [parkingTariffId=" + parkingTariffId + ", locationId=" + locationId + ", vehicleTypeId="
				+ vehicleTypeId + ", fromTime=" + fromTime + ", toTime=" + toTime + ", tariff=" + tariff
				+ ", repeatMinutes=" + repeatMinutes + ", repeatTariff=" + repeatTariff + "]";
	}

	
	
	
}
