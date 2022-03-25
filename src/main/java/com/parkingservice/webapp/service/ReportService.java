package com.parkingservice.webapp.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.parkingservice.webapp.bean.DailyReport;
import com.parkingservice.webapp.model.ParkingTransaction;

public interface ReportService {
	
	public List<ParkingTransaction> getPOSTransactionList(@Param("userId") int userId);
	public Map<String, DailyReport> getDailyReportData(String date, int locationId);
	public Map<Integer, Integer> getCashierPaymentStats(int cashierId, String fromTime, String toTime);
	public Map<Integer, Integer> getCashierLotStats(int cashierId, String fromTime, String toTime);
	public Map<String, Integer> getCashierExemptStats(int cashierId, String fromTime, String toTime);
	public List<ParkingTransaction> getTransactionList(Map<String,String> searchParams);
	public Map<String, DailyReport> getDatewiseReportData(String fromDate, String toDate, int locationId);
}
