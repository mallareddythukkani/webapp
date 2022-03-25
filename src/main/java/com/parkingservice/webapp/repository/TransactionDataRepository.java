package com.parkingservice.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkingservice.webapp.model.TransactionData;

public interface TransactionDataRepository extends JpaRepository<TransactionData, Long> {
	
}
