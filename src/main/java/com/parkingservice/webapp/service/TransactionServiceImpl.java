package com.parkingservice.webapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parkingservice.webapp.dao.ParkingTransactionDAO;
import com.parkingservice.webapp.model.ParkingLocation;
import com.parkingservice.webapp.model.ParkingTransaction;
import com.parkingservice.webapp.model.PtExemptData;
import com.parkingservice.webapp.model.TransactionData;
import com.parkingservice.webapp.model.VehicleType;
import com.parkingservice.webapp.repository.ParkingLocationRepository;
import com.parkingservice.webapp.repository.ParkingTransactionRepository;
import com.parkingservice.webapp.repository.PtExemptDataRepository;
import com.parkingservice.webapp.repository.TransactionDataRepository;
import com.parkingservice.webapp.repository.VehicleTypeRepository;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	ParkingTransactionRepository ptRepo;
	
	@Autowired
	ParkingTransactionDAO ptDAO;
	
	@Autowired
	PtExemptDataRepository ptExemptRepo;
	
	@Autowired
	ParkingLocationRepository plRepo;
	
	@Autowired
	VehicleTypeRepository vtRepo;
	
	@Autowired
	TransactionDataRepository tdRepo;
	
	@Override
	public ParkingTransaction findByTransactionId(long transactionId) {
		return ptRepo.findByTransactionId(transactionId);
	}

	@Override
	public String getTransTypeById(int transTypeId) {
		return ptDAO.getTransTypeById(transTypeId);
	}
	
	@Override
	public int getTariffAmount(String entryTime, String exitTime, int locationId, int vehicleTypeId) {
		return ptDAO.getTariffAmount(entryTime, exitTime, locationId, vehicleTypeId);
	}
	
	@Override
	public ParkingTransaction save(ParkingTransaction trans) {
		return ptDAO.save(trans);
	}

	@Override
	public TransactionData save(TransactionData trans) {
		return tdRepo.save(trans);
	}
	
	@Override
	public boolean save(PtExemptData exmpData) {
		PtExemptData perow = ptExemptRepo.save(exmpData);
		return (perow!=null)?true:false;
	}
	
	@Override
	public ParkingTransaction findTransactionBySerialNumber(String slNo) {
		return ptDAO.findBySerialNumber(slNo);
	}

	@Override
	public ParkingLocation findParkingLocationById(int id) {
		ParkingLocation pl = null;
		Optional<ParkingLocation> plRes = plRepo.findById(id);
		if(plRes.isPresent()) {
			pl = plRes.get();
		}
		return pl;
	}

	@Override
	public VehicleType findVehicleTypeById(short id) {
		VehicleType vt = null;
		Optional<VehicleType> vtRes = vtRepo.findById(id);
		if(vtRes.isPresent()) {
			vt = vtRes.get();
		}
		return vt;
	}

	@Override
	public String getPreference(int locationId, String section, String name) {
		return ptDAO.getPreference(locationId, section, name);
	}

}
