package com.parkingservice.webapp.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.parkingservice.webapp.model.LOT;
import com.parkingservice.webapp.model.ParkingTariff;
import com.parkingservice.webapp.model.ParkingTransaction;
import com.parkingservice.webapp.util.Helper;

@Repository
@Transactional
public class ParkingTransactionDAOImpl implements ParkingTransactionDAO {
	
	//define entity manager
	@Autowired
	private EntityManager entityManager;

	@Override
	
	public ParkingTransaction save(ParkingTransaction trans) {
		//save or update the parking transaction
		ParkingTransaction pt_row = entityManager.merge(trans);
		trans.setTransactionId(pt_row.getTransactionId());
		return pt_row;
	}

	
	@Override
	public ParkingTransaction findById(long transactionId) {
		return entityManager.find(ParkingTransaction.class, transactionId);
	}

	@Override
	public ParkingTransaction findBySerialNumber(String slNo) {
		//writing native SQL query
		String qry = "SELECT * FROM parking_transaction pt WHERE 1 ";
		if(slNo.length()<=4) {
			qry += "AND RIGHT(transaction_id,"+slNo.length()+")="+slNo;
		}
		else
			qry += "AND transaction_id="+slNo;
		qry += " ORDER BY transaction_id DESC LIMIT 1";
		Query q = entityManager.createNativeQuery(qry,ParkingTransaction.class);
		try {
			return (ParkingTransaction) q.getSingleResult();
		}
		catch(NoResultException ex) {
			return null;
		}
	}

	@Override
	public int getTariffAmount(String entryTime, String exitTime, int locationId, int vehicleTypeId) {
		int amount = -1;
		int timeMin = Helper.getTimeDiffInMinutes(entryTime, exitTime);
		//writing native SQL query
		String qry = "SELECT tariff FROM parking_tariff "
				+ " WHERE location_id="+locationId
				+ " AND vehicle_type_id = "+vehicleTypeId
				+ " AND from_time <="+timeMin
				+ " AND to_time >="+timeMin;
		Query q = entityManager.createNativeQuery(qry);
		try {
			Object rs = q.getSingleResult();
			amount = (Integer) rs;
			
		} //2021-01-05 18:22:45
		catch(NoResultException ex) {
			//writing native SQL query
			String qry2 = "SELECT tariff,to_time,repeat_tariff,repeat_minutes FROM parking_tariff "
					+ " WHERE location_id="+locationId
					+ " AND vehicle_type_id = "+vehicleTypeId
					+ " AND repeat_minutes IS NOT NULL "
					+ " AND repeat_tariff IS NOT NULL ";
			Query q2 = entityManager.createNativeQuery(qry2);
			List<Object[]> rs = q2.getResultList();
			for(Object[] result: rs) {
				amount = (Integer) result[0];
				int toTime = (Integer) result[1];
				int repeatTariff = (Integer) result[2];
				int repeatMinutes = (Integer) result[3];
				int minutes = timeMin - toTime;
				amount += Math.round(Math.ceil((float)minutes/repeatMinutes)*repeatTariff);
			}
		}
		return amount;
	}

	

	@Override
	public String getPreference(int locationId,String section, String name) {
		String qry = "SELECT value FROM preference WHERE location_id=:locationId AND section=:section AND name=:name";
		String val=null;
		List<Object[]> results= entityManager.createNativeQuery(qry).
				  setParameter("locationId", locationId).
				  setParameter("section", section).
				  setParameter("name", name).getResultList();
		if(results.size()>0) {
			Object valObj = results.get(0);
			val = (String) valObj;
		}
		return val;
	}

	@Override
	public LOT save(LOT lot) {
		return entityManager.merge(lot);
	}

	@Override
	public List<LOT> getLotLogs(int userId) {
		TypedQuery<LOT> qry = entityManager.createQuery("SELECT l FROM LOT l "
				+ "WHERE l.createdBy=:userId AND DATE(l.createdTime)=CURRENT_DATE "
				+ "ORDER BY l.createdTime DESC",LOT.class);
		qry.setParameter("userId", userId);
		return qry.getResultList();
	}

	@Override
	public List<ParkingTariff> getParkingTariffList(int locationId, int vehicleTypeId) {
		TypedQuery<ParkingTariff> qry = entityManager.createQuery("SELECT pt FROM ParkingTariff pt "
				+ "WHERE pt.locationId=:locationId AND pt.vehicleTypeId=:vehicleTypeId",ParkingTariff.class);
		qry.setParameter("locationId", locationId);
		qry.setParameter("vehicleTypeId", vehicleTypeId);
		return qry.getResultList();
	}


	@Override
	public String getTransTypeById(int transTypeId) {
		String qry = "SELECT name FROM trans_type WHERE trans_type_id=:transTypeId ";
		String val=null;
		@SuppressWarnings("unchecked")
		List<Object[]> results= entityManager.createNativeQuery(qry).
				  setParameter("transTypeId", transTypeId).getResultList();
		if(results.size()>0) {
			Object valObj = results.get(0);
			val = (String) valObj;
		}
		return val;
	}
	
	@Override
	public int setPreference(int locationId,String section, String name, String value) {
		String qry = "UPDATE preference SET value=:value WHERE location_id=:locationId AND section=:section AND name=:name";
		int res= entityManager.createNativeQuery(qry).
				  setParameter("locationId", locationId).
				  setParameter("section", section).
				  setParameter("name", name).
				  setParameter("value", value).executeUpdate();
		return res;
	}


	@Override
	public List<ParkingTransaction> getTransactionList(Map<String, String> searchParams) {
		//writing native SQL query
		String sql = "SELECT pt FROM ParkingTransaction pt JOIN pt.vehicleType vt WHERE (pt.entryTime BETWEEN :fromTime AND :toTime)";
		short vehicleTypeId = 0;
		if(searchParams.get("vehicleTypeId")!= null && !searchParams.get("vehicleTypeId").isEmpty()) {
			sql += " AND vt.vehicleTypeId = :vehicleTypeId ";
			vehicleTypeId = Short.parseShort(searchParams.get("vehicleTypeId"));
		}
		sql += " ORDER BY pt.entryTime ";
		TypedQuery<ParkingTransaction> qry = entityManager.createQuery(sql,ParkingTransaction.class);
		qry.setParameter("fromTime", searchParams.get("fromTime"));
		qry.setParameter("toTime", searchParams.get("toTime"));
		if(vehicleTypeId>0) {
			qry.setParameter("vehicleTypeId", vehicleTypeId);
		}
		//qry.setMaxResults(10);
		return qry.getResultList();
	}

}
