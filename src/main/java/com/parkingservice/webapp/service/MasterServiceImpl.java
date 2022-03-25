package com.parkingservice.webapp.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parkingservice.webapp.dao.ParkingTransactionDAO;
import com.parkingservice.webapp.model.ExitGate;
import com.parkingservice.webapp.model.LOT;
import com.parkingservice.webapp.model.ParkingTariff;
import com.parkingservice.webapp.model.Role;
import com.parkingservice.webapp.model.Tenant;
import com.parkingservice.webapp.model.User;
import com.parkingservice.webapp.model.UserEmployee;
import com.parkingservice.webapp.model.UserLog;
import com.parkingservice.webapp.model.VehicleType;
import com.parkingservice.webapp.repository.ExitGateRepository;
import com.parkingservice.webapp.repository.LotRepository;
import com.parkingservice.webapp.repository.ParkingTariffRepository;
import com.parkingservice.webapp.repository.RoleRepository;
import com.parkingservice.webapp.repository.TenantRepository;
import com.parkingservice.webapp.repository.UserEmployeeRepository;
import com.parkingservice.webapp.repository.UserLogRepository;
import com.parkingservice.webapp.repository.UserRepository;
import com.parkingservice.webapp.repository.VehicleTypeRepository;
import com.parkingservice.webapp.util.Helper;

@Service
@Transactional
public class MasterServiceImpl implements MasterService {

	@Autowired
	private TenantRepository tenantRepository;
	@Autowired
	private UserLogRepository userLogRepository;
	@Autowired
	private UserEmployeeRepository userEmployeeRepository;
	@Autowired
	private LotRepository lotRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ExitGateRepository exitGateRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ParkingTransactionDAO ptDAO;
	@Autowired
	private ParkingTariffRepository tariffRepository;
	@Autowired
	private VehicleTypeRepository vehicleTypeRepository;
	
	@Override
	public List<Tenant> getList() {
		return tenantRepository.findAll();
	}

	@Override
	public int save(UserLog userLog) {
		UserLog log = userLogRepository.save(userLog);
		return log.getUserLogId();
	}
	
	@Override
	public int save(Tenant tenant) {
		Tenant trow = tenantRepository.save(tenant);
		return trow.getTenantId();
	}

	@Override
	public Tenant get(int tenantId) {
		return tenantRepository.getOne(tenantId);
	}

	@Override
	public UserEmployee findById(int userId) {
		return userEmployeeRepository.findByUserId(userId);
	}

	@Override
	public int save(LOT lot) {
		LOT trow = lotRepository.save(lot);
		return trow.getLotId();
	}

	@Override
	public List<LOT> getLOTList(int userId) {
		return lotRepository.getPOSLOTList(userId);
	}

	@Override
	public LOT findByLotId(int lotId) {
		return lotRepository.findByLotId(lotId);
	}

	@Override
	public List<User> getUserList(int[] roleStr) {
		return userRepository.getUserList(roleStr);
	}

	@Override
	public int save(User user) {
		User urow = userRepository.save(user);
		return urow.getUserId();
	}

	@Override
	public ExitGate getExitGate(int exitGateId) {
		return exitGateRepository.getOne(exitGateId);
	}

	@Override
	public int save(UserEmployee userEmp) {
		UserEmployee urow = userEmployeeRepository.save(userEmp);
		return urow.getUserId();
	}

	@Override
	public List<Role> getAllowedRoleList(int roleId) {
		int[] roleList = Helper.getAllowedRoleStr(roleId);
		List<Integer> roleIds = Arrays.stream(roleList).boxed().collect(Collectors.toList());
		return roleRepository.findAllById(roleIds);
	}

	@Override
	public List<ExitGate> getExitGateList(int locationId) {
		return exitGateRepository.getExitGateList(locationId);
	}

	@Override
	public User findUserById(int userId) {
		return userRepository.getOne(userId);
	}

	@Override
	public List<ParkingTariff> getParkingTariffList(int locationId, int vehicleTypeId) {
		return ptDAO.getParkingTariffList(locationId, vehicleTypeId);
	}

	@Override
	public String getPreference(int locationId, String section, String name) {
		return ptDAO.getPreference(locationId, section, name);
	}

	@Override
	public boolean updateTariff(int locationId, int vehicleTypeId, List<ParkingTariff> ptList) {
		boolean res = false;
		try {
			//remove existing tariff records
			tariffRepository.deleteTariffData(locationId, vehicleTypeId);
			//save new tariff records
			for(ParkingTariff ptRow: ptList) {
				ParkingTariff pt = tariffRepository.save(ptRow);
			}
			res = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public int setPreference(int locationId, String section, String name, String value) {
		return ptDAO.setPreference(locationId, section, name, value);
	}

	@Override
	public List<User> getCashierList(int locationId) {
		return userRepository.getCashierList(locationId);
	}

	@Override
	public List<VehicleType> getVehicleTypeList() {
		return vehicleTypeRepository.findAll();
	}

}
