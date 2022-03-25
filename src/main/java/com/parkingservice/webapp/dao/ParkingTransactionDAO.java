package com.parkingservice.webapp.dao;

import java.util.List;
import java.util.Map;

import com.parkingservice.webapp.model.LOT;
import com.parkingservice.webapp.model.ParkingTariff;
import com.parkingservice.webapp.model.ParkingTransaction;

public interface ParkingTransactionDAO {
	public ParkingTransaction save(ParkingTransaction trans);
	public ParkingTransaction findById(long transactionId);
	public ParkingTransaction findBySerialNumber(String slNo);
	public int getTariffAmount(String entryTime, String exitTime, int locationId, int vehicleTypeId);
	public String getPreference(int locationId,String section,String name);
	public LOT save(LOT lot);
	public List<LOT> getLotLogs(int userId);
	public List<ParkingTariff> getParkingTariffList(int locationId, int vehicleTypeId);
	public String getTransTypeById(int transTypeId);
	public int setPreference(int locationId,String section, String name, String value);
	public List<ParkingTransaction> getTransactionList(Map<String,String> searchParams);
}
