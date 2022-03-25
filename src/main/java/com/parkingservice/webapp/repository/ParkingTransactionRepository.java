package com.parkingservice.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parkingservice.webapp.model.ParkingTransaction;

public interface ParkingTransactionRepository extends JpaRepository<ParkingTransaction, Long> {
	public ParkingTransaction findByTransactionId(long transactionId);
	
	@Query("SELECT pt FROM ParkingTransaction pt "
		 + "JOIN  pt.transactionData td WHERE pt.amountCollectBy=:userId AND "
		 + "td.exitDeviceType=3 AND DATE(pt.exitTime)=CURRENT_DATE() ORDER BY pt.exitTime desc")
	public List<ParkingTransaction> getPOSTransactionList(@Param("userId") int userId);
}
