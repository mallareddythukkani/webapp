package com.parkingservice.webapp.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parkingservice.webapp.bean.DailyReport;
import com.parkingservice.webapp.dao.ParkingTransactionDAO;
import com.parkingservice.webapp.dao.ReportDAO;
import com.parkingservice.webapp.model.ParkingTransaction;
import com.parkingservice.webapp.repository.ParkingTransactionRepository;
import com.parkingservice.webapp.util.Helper;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

	@Autowired
	ParkingTransactionRepository ptRepo;
	@Autowired
	ReportDAO reportDAO;
	@Autowired
	ParkingTransactionDAO ptDAO;
	
	@Override
	public List<ParkingTransaction> getPOSTransactionList(int userId) {
		// TODO Auto-generated method stub
		return ptRepo.getPOSTransactionList(userId);
	}

	@Override
	public Map<String, DailyReport> getDailyReportData(String date, int locationId) {
		
		//GET TOTAL VEHICLE COUNT
		String totCntQry = "SELECT count(*) as cnt, vehicle_type_id from "
				+ "parking_transaction where (status=1 || (status=0 && exit_time is not NULL)) AND DATE(entry_time) = '"+date+"' AND location_id = "+locationId+" group by vehicle_type_id";
		List<Object[]> totCntRs = reportDAO.executeNativeQuery(totCntQry);
		DailyReport dr4w = new DailyReport();
		DailyReport dr2w = new DailyReport();
		for(Object[] result: totCntRs) {
			short vehicleTypeId = (Short) result[1];
			BigInteger  totCnt = (BigInteger) result[0];
			if(vehicleTypeId==1) {
				dr4w.setVehicleCnt(totCnt.intValue());
			}
			if(vehicleTypeId==2) {
				dr2w.setVehicleCnt(totCnt.intValue());
			}
		}
		
		String nextDate = Helper.getNextDate(date);
		
		//GET COLLECTOIN DETAILS
		String colQry = "SELECT count(*) as cnt,SUM(amount) as amt, vehicle_type_id from "
				+ "parking_transaction where 1 AND DATE(entry_time) = '"+date+"' "
				+ "AND location_id = "+locationId+" AND (exit_time BETWEEN '"+date+" 03:00:00' AND '"+nextDate+" 03:00:00') "
				+ "AND exempted=0 and amount>0 "
				+ "group by vehicle_type_id";
		List<Object[]> colRs = reportDAO.executeNativeQuery(colQry);
		for(Object[] result: colRs) {
			short vehicleTypeId = (Short) result[2];
			BigInteger  colCnt = (BigInteger) result[0];
			BigDecimal  colAmt = (BigDecimal) result[1];
			if(vehicleTypeId==1) {
				dr4w.setCollectionCnt(colCnt.intValue());
				dr4w.setCollectionAmt(colAmt.intValue());
			}
			if(vehicleTypeId==2) {
				dr2w.setCollectionCnt(colCnt.intValue());
				dr2w.setCollectionAmt(colAmt.intValue());
			}
		}
		
		
		
		//GET LOT DETAILS
		String lotQry = "SELECT count(*) as cnt,SUM(amount_collected) as amt, vehicle_type_id "
				+ "from lot where 1 AND (created_time BETWEEN '"+date+" 03:00:00' AND '"+nextDate+" 03:00:00') "
				+ "AND location_id = "+locationId+" "
				+ "group by vehicle_type_id";
		List<Object[]> lotRs = reportDAO.executeNativeQuery(lotQry);
		for(Object[] result: lotRs) {
			short vehicleTypeId = (Short) result[2];
			BigInteger  colCnt = (BigInteger) result[0];
			BigDecimal  colAmt = (BigDecimal) result[1];
			if(vehicleTypeId==1) {
				dr4w.setLotCnt(colCnt.intValue());
				dr4w.setLotAmt(colAmt.intValue());
			}
			if(vehicleTypeId==2) {
				dr2w.setLotCnt(colCnt.intValue());
				dr2w.setLotAmt(colAmt.intValue());
			}
		}
		
		//GET OVERNIGHT DETAILS
		String overnightQry = "SELECT count(*) as cnt, SUM(amount) as amt,vehicle_type_id "
				+ "from parking_transaction where 1 AND (exit_time BETWEEN '"+date+" 03:00:00' AND '"+nextDate+" 03:00:00') "
				+ "AND (exit_time > CAST(CONCAT(DATE_ADD(DATE(entry_time), INTERVAL 1 DAY), ' 03:00:00') AS datetime)) "
				+ "AND location_id = "+locationId+" AND exempted=0 and amount>=0 "
				+ "group by vehicle_type_id";
		List<Object[]> overnightRs = reportDAO.executeNativeQuery(overnightQry);
		for(Object[] result: overnightRs) {
			short vehicleTypeId = (Short) result[2];
			BigInteger  colCnt = (BigInteger) result[0];
			BigDecimal  colAmt = (BigDecimal) result[1];
			if(vehicleTypeId==1) {
				dr4w.setOvernightCnt(colCnt.intValue());
				dr4w.setOvernightAmt(colAmt.intValue());
			}
			if(vehicleTypeId==2) {
				dr2w.setOvernightCnt(colCnt.intValue());
				dr2w.setOvernightAmt(colAmt.intValue());
			}
		}

		//GET ZERO AMOUNT DETAILS
		String zeroQry = "SELECT count(*) as cnt, vehicle_type_id from "
				+ "parking_transaction where 1 AND DATE(entry_time) = '"+date+"' "
				+ "AND location_id = "+locationId+" AND (exit_time BETWEEN '"+date+" 03:00:00' AND '"+nextDate+" 03:00:00') "
				+ "AND exempted=0 and amount=0 AND exit_time IS NOT NULL "
				+ "group by vehicle_type_id";
		List<Object[]> zeroRs = reportDAO.executeNativeQuery(zeroQry);
		for(Object[] result: zeroRs) {
			short vehicleTypeId = (Short) result[1];
			BigInteger  colCnt = (BigInteger) result[0];
			if(vehicleTypeId==1) {
				dr4w.setZeroCnt(colCnt.intValue());
			}
			if(vehicleTypeId==2) {
				dr2w.setZeroCnt(colCnt.intValue());
			}
		}
		
		//GET EXEMPTED DETAILS
		String exemptQry = "SELECT count(*) as cnt, vehicle_type_id from "
				+ "parking_transaction where 1 AND DATE(entry_time) = '"+date+"' "
				+ "AND location_id = "+locationId+" AND (exit_time BETWEEN '"+date+" 03:00:00' AND '"+nextDate+" 03:00:00')  "
				+ "AND exempted=1 "
				+ "group by vehicle_type_id";
		List<Object[]> exemptRs = reportDAO.executeNativeQuery(exemptQry);
		for(Object[] result: exemptRs) {
			short vehicleTypeId = (Short) result[1];
			BigInteger  colCnt = (BigInteger) result[0];
			if(vehicleTypeId==1) {
				dr4w.setExemptedCnt(colCnt.intValue());
			}
			if(vehicleTypeId==2) {
				dr2w.setExemptedCnt(colCnt.intValue());
			}
		}
		
		//GET NOT SETTLED DETAILS
		String notSettledQry = "SELECT count(*) as cnt, vehicle_type_id from "
				+ "parking_transaction where status=1 AND DATE(entry_time) = '"+date+"' "
				+ "AND location_id = "+locationId+" AND exit_time IS NULL "
				+ "group by vehicle_type_id";
		List<Object[]> notSettledRs = reportDAO.executeNativeQuery(notSettledQry);
		for(Object[] result: notSettledRs) {
			short vehicleTypeId = (Short) result[1];
			BigInteger  colCnt = (BigInteger) result[0];
			if(vehicleTypeId==1) {
				dr4w.setNotSettledCnt(colCnt.intValue());
			}
			if(vehicleTypeId==2) {
				dr2w.setNotSettledCnt(colCnt.intValue());
			}
		}
		
		//GET SETTLED NEXT DAY DETAILS
		String settledNxtDayQry = "SELECT count(*) as cnt, vehicle_type_id from "
				+ "parking_transaction where 1 AND DATE(entry_time) = '"+date+"' "
				+ "AND location_id = "+locationId+" AND exit_time > '"+nextDate+" 03:00:00' "
				+ "group by vehicle_type_id";
		List<Object[]> settledNxtDayRs = reportDAO.executeNativeQuery(settledNxtDayQry);
		for(Object[] result: settledNxtDayRs) {
			short vehicleTypeId = (Short) result[1];
			BigInteger  colCnt = (BigInteger) result[0];
			if(vehicleTypeId==1) {
				dr4w.setSettledNextDayCnt(colCnt.intValue());
			}
			if(vehicleTypeId==2) {
				dr2w.setSettledNextDayCnt(colCnt.intValue());
			}
		}		
			
		Map<String,DailyReport> dailyReport = new HashMap<String,DailyReport>();
		dailyReport.put("4w", dr4w);
		dailyReport.put("2w", dr2w);
		return dailyReport;
	}

	@Override
	public Map<Integer, Integer> getCashierPaymentStats(int cashierId, String fromTime,
			String toTime) {
		String paymentQry = "SELECT count(*) as cnt, amount from parking_transaction "
				+ "where 1 AND (exit_time BETWEEN '"+fromTime+"' AND '"+toTime+"') AND "
				+ "exempted=0 and amount>=0 AND amount_collect_by = "+cashierId+" "
				+ "group by amount ORDER BY amount DESC ";
		List<Object[]> paymentRs = reportDAO.executeNativeQuery(paymentQry);
		Map<Integer,Integer> res = new TreeMap<>(Collections.reverseOrder());
		for(Object[] result: paymentRs) {
			BigInteger  colCnt = (BigInteger) result[0];
			int  colAmt = (Integer) result[1];
			res.put(colAmt, colCnt.intValue());
			
		}	
		if(res.size()==0)
			res = null;
		return res;
	}

	@Override
	public Map<Integer, Integer> getCashierLotStats(int cashierId, String fromTime, String toTime) {
		String lotQry = "SELECT count(*) as cnt, amount_collected from lot "
				+ "where 1 AND (created_time BETWEEN '"+fromTime+"' AND '"+toTime+"') AND "
				+ "created_by = "+cashierId+" "
				+ "group by amount_collected ORDER BY amount_collected DESC ";
		List<Object[]> lotRs = reportDAO.executeNativeQuery(lotQry);
		Map<Integer,Integer> res = new TreeMap<>(Collections.reverseOrder());
		for(Object[] result: lotRs) {
			BigInteger  colCnt = (BigInteger) result[0];
			int  colAmt = (Integer) result[1];
			res.put(colAmt, colCnt.intValue());
			
		}
		if(res.size()==0)
			res = null;
		return res;
	}

	@Override
	public Map<String, Integer> getCashierExemptStats(int cashierId, String fromTime, String toTime) {
		String paymentQry = "SELECT count(*) as cnt, er.name as exempt_reason from parking_transaction pt "
				+ "JOIN exempt_reason er ON pt.exempt_reason_id = er.exempt_reason_id "
				+ "where 1 AND (pt.exit_time BETWEEN '"+fromTime+"' AND '"+toTime+"') AND "
				+ "pt.exempted=1  AND pt.amount_collect_by = "+cashierId+" "
				+ "group by pt.exempt_reason_id ORDER BY pt.exempt_reason_id ASC ";
		List<Object[]> paymentRs = reportDAO.executeNativeQuery(paymentQry);
		Map<String,Integer> res = new TreeMap<>();
		for(Object[] result: paymentRs) {
			BigInteger  colCnt = (BigInteger) result[0];
			String exempt_reason = (String) result[1];
			res.put(exempt_reason, colCnt.intValue());
			
		}	
		if(res.size()==0)
			res = null;
		return res;
	}

	@Override
	public List<ParkingTransaction> getTransactionList(Map<String, String> searchParams) {
		return ptDAO.getTransactionList(searchParams);
	}
	

	@Override
	public Map<String, DailyReport> getDatewiseReportData(String fromDate, String toDate, int locationId) {
		
		//GET TOTAL VEHICLE COUNT
		String totCntQry = "SELECT count(*) as cnt, vehicle_type_id from "
				+ "parking_transaction where (status=1 || (status=0 && exit_time is not NULL)) AND (DATE(entry_time) BETWEEN '"+fromDate+"' AND '"+toDate+"') AND location_id = "+locationId+" group by vehicle_type_id";
		List<Object[]> totCntRs = reportDAO.executeNativeQuery(totCntQry);
		DailyReport dr4w = new DailyReport();
		DailyReport dr2w = new DailyReport();
		for(Object[] result: totCntRs) {
			short vehicleTypeId = (Short) result[1];
			BigInteger  totCnt = (BigInteger) result[0];
			if(vehicleTypeId==1) {
				dr4w.setVehicleCnt(totCnt.intValue());
			}
			if(vehicleTypeId==2) {
				dr2w.setVehicleCnt(totCnt.intValue());
			}
		}
		
		String nextDate = Helper.getNextDate(toDate);
		
		//GET COLLECTOIN DETAILS
		String colQry = "SELECT count(*) as cnt,SUM(amount) as amt, vehicle_type_id from "
				+ "parking_transaction where 1 AND (DATE(entry_time) BETWEEN '"+fromDate+"' AND '"+toDate+"') "
				+ "AND location_id = "+locationId+" AND (exit_time BETWEEN '"+fromDate+" 03:00:00' AND '"+nextDate+" 03:00:00') "
				+ "AND exempted=0 and amount>0 "
				+ "group by vehicle_type_id";
		List<Object[]> colRs = reportDAO.executeNativeQuery(colQry);
		for(Object[] result: colRs) {
			short vehicleTypeId = (Short) result[2];
			BigInteger  colCnt = (BigInteger) result[0];
			BigDecimal  colAmt = (BigDecimal) result[1];
			if(vehicleTypeId==1) {
				dr4w.setCollectionCnt(colCnt.intValue());
				dr4w.setCollectionAmt(colAmt.intValue());
			}
			if(vehicleTypeId==2) {
				dr2w.setCollectionCnt(colCnt.intValue());
				dr2w.setCollectionAmt(colAmt.intValue());
			}
		}
		
		
		
		//GET LOT DETAILS
		String lotQry = "SELECT count(*) as cnt,SUM(amount_collected) as amt, vehicle_type_id "
				+ "from lot where 1 AND (created_time BETWEEN '"+fromDate+" 03:00:00' AND '"+nextDate+" 03:00:00') "
				+ "AND location_id = "+locationId+" "
				+ "group by vehicle_type_id";
		List<Object[]> lotRs = reportDAO.executeNativeQuery(lotQry);
		for(Object[] result: lotRs) {
			short vehicleTypeId = (Short) result[2];
			BigInteger  colCnt = (BigInteger) result[0];
			BigDecimal  colAmt = (BigDecimal) result[1];
			if(vehicleTypeId==1) {
				dr4w.setLotCnt(colCnt.intValue());
				dr4w.setLotAmt(colAmt.intValue());
			}
			if(vehicleTypeId==2) {
				dr2w.setLotCnt(colCnt.intValue());
				dr2w.setLotAmt(colAmt.intValue());
			}
		}
		
		//GET OVERNIGHT DETAILS
		String overnightQry = "SELECT count(*) as cnt, SUM(amount) as amt,vehicle_type_id "
				+ "from parking_transaction where 1 AND (exit_time BETWEEN '"+fromDate+" 03:00:00' AND '"+nextDate+" 03:00:00') "
				+ "AND (exit_time > CAST(CONCAT(DATE_ADD(DATE(entry_time), INTERVAL 1 DAY), ' 03:00:00') AS datetime)) "
				+ "AND location_id = "+locationId+" AND exempted=0 and amount>=0 "
				+ "group by vehicle_type_id";
		List<Object[]> overnightRs = reportDAO.executeNativeQuery(overnightQry);
		for(Object[] result: overnightRs) {
			short vehicleTypeId = (Short) result[2];
			BigInteger  colCnt = (BigInteger) result[0];
			BigDecimal  colAmt = (BigDecimal) result[1];
			if(vehicleTypeId==1) {
				dr4w.setOvernightCnt(colCnt.intValue());
				dr4w.setOvernightAmt(colAmt.intValue());
			}
			if(vehicleTypeId==2) {
				dr2w.setOvernightCnt(colCnt.intValue());
				dr2w.setOvernightAmt(colAmt.intValue());
			}
		}

		//GET ZERO AMOUNT DETAILS
		String zeroQry = "SELECT count(*) as cnt, vehicle_type_id from "
				+ "parking_transaction where 1 AND (DATE(entry_time) BETWEEN '"+fromDate+"' AND '"+toDate+"') "
				+ "AND location_id = "+locationId+" AND (exit_time BETWEEN '"+fromDate+" 03:00:00' AND '"+nextDate+" 03:00:00') "
				+ "AND exempted=0 and amount=0 AND exit_time IS NOT NULL "
				+ "group by vehicle_type_id";
		List<Object[]> zeroRs = reportDAO.executeNativeQuery(zeroQry);
		for(Object[] result: zeroRs) {
			short vehicleTypeId = (Short) result[1];
			BigInteger  colCnt = (BigInteger) result[0];
			if(vehicleTypeId==1) {
				dr4w.setZeroCnt(colCnt.intValue());
			}
			if(vehicleTypeId==2) {
				dr2w.setZeroCnt(colCnt.intValue());
			}
		}
		
		//GET EXEMPTED DETAILS
		String exemptQry = "SELECT count(*) as cnt, vehicle_type_id from "
				+ "parking_transaction where 1 AND (DATE(entry_time) BETWEEN '"+fromDate+"' AND '"+toDate+"') "
				+ "AND location_id = "+locationId+" AND (exit_time BETWEEN '"+fromDate+" 03:00:00' AND '"+nextDate+" 03:00:00')  "
				+ "AND exempted=1 "
				+ "group by vehicle_type_id";
		List<Object[]> exemptRs = reportDAO.executeNativeQuery(exemptQry);
		for(Object[] result: exemptRs) {
			short vehicleTypeId = (Short) result[1];
			BigInteger  colCnt = (BigInteger) result[0];
			if(vehicleTypeId==1) {
				dr4w.setExemptedCnt(colCnt.intValue());
			}
			if(vehicleTypeId==2) {
				dr2w.setExemptedCnt(colCnt.intValue());
			}
		}
		
		//GET NOT SETTLED DETAILS
		String notSettledQry = "SELECT count(*) as cnt, vehicle_type_id from "
				+ "parking_transaction where status=1 AND (DATE(entry_time) BETWEEN '"+fromDate+"' AND '"+toDate+"') "
				+ "AND location_id = "+locationId+" AND exit_time IS NULL "
				+ "group by vehicle_type_id";
		List<Object[]> notSettledRs = reportDAO.executeNativeQuery(notSettledQry);
		for(Object[] result: notSettledRs) {
			short vehicleTypeId = (Short) result[1];
			BigInteger  colCnt = (BigInteger) result[0];
			if(vehicleTypeId==1) {
				dr4w.setNotSettledCnt(colCnt.intValue());
			}
			if(vehicleTypeId==2) {
				dr2w.setNotSettledCnt(colCnt.intValue());
			}
		}
		
		//GET SETTLED NEXT DAY DETAILS
		String settledNxtDayQry = "SELECT count(*) as cnt, vehicle_type_id from "
				+ "parking_transaction where 1 AND DATE(entry_time) = '"+toDate+"' "
				+ "AND location_id = "+locationId+" AND exit_time > '"+nextDate+" 03:00:00' "
				+ "group by vehicle_type_id";
		List<Object[]> settledNxtDayRs = reportDAO.executeNativeQuery(settledNxtDayQry);
		for(Object[] result: settledNxtDayRs) {
			short vehicleTypeId = (Short) result[1];
			BigInteger  colCnt = (BigInteger) result[0];
			if(vehicleTypeId==1) {
				dr4w.setSettledNextDayCnt(colCnt.intValue());
			}
			if(vehicleTypeId==2) {
				dr2w.setSettledNextDayCnt(colCnt.intValue());
			}
		}		
			
		Map<String,DailyReport> dailyReport = new HashMap<String,DailyReport>();
		dailyReport.put("4w", dr4w);
		dailyReport.put("2w", dr2w);
		return dailyReport;
	}

}
