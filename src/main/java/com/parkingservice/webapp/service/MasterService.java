package com.parkingservice.webapp.service;

import java.util.List;

import com.parkingservice.webapp.model.ExitGate;
import com.parkingservice.webapp.model.LOT;
import com.parkingservice.webapp.model.ParkingTariff;
import com.parkingservice.webapp.model.Role;
import com.parkingservice.webapp.model.Tenant;
import com.parkingservice.webapp.model.User;
import com.parkingservice.webapp.model.UserEmployee;
import com.parkingservice.webapp.model.UserLog;
import com.parkingservice.webapp.model.VehicleType;

public interface MasterService {
	public List<Tenant> getList();
	public int save(UserLog userLog);
	public int save(Tenant tenant);
	public Tenant get(int tenantId);
	public UserEmployee findById(int userId);
	public int save(LOT lot);
	public List<LOT> getLOTList(int userId);
	public LOT findByLotId(int lotId);
	public List<User> getUserList(int[] roleStr);
	public int save(User user);
	public ExitGate getExitGate(int exitGateId);
	public int save(UserEmployee userEmp);
	public List<Role> getAllowedRoleList(int roleId);
	public List<ExitGate> getExitGateList(int locationId);
	public User findUserById(int userId);
	public List<ParkingTariff> getParkingTariffList(int locationId, int vehicleTypeId);
	public String getPreference(int locationId,String section,String name);
	public boolean updateTariff(int locationId, int vehicleTypeId, List<ParkingTariff> ptList);
	public int setPreference(int locationId,String section, String name, String value);
	public List<User> getCashierList(int locationId);
	public List<VehicleType> getVehicleTypeList();
}
