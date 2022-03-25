package com.parkingservice.webapp.service;

import com.parkingservice.webapp.model.ParkingLocation;
import com.parkingservice.webapp.model.ParkingTransaction;
import com.parkingservice.webapp.model.PtExemptData;
import com.parkingservice.webapp.model.TransactionData;
import com.parkingservice.webapp.model.VehicleType;

public interface TransactionService {
	
	ParkingTransaction findByTransactionId(long transactionId);
	String getTransTypeById(int transTypeId);
	public int getTariffAmount(String entryTime, String exitTime, int locationId, int vehicleTypeId);
	public ParkingTransaction save(ParkingTransaction trans);
	public TransactionData save(TransactionData trans);
	public boolean save(PtExemptData exmpData);
	public ParkingTransaction findTransactionBySerialNumber(String slNo);
	public ParkingLocation findParkingLocationById(int id);
	public VehicleType findVehicleTypeById(short id);
	public String getPreference(int locationId,String section, String name);
}
