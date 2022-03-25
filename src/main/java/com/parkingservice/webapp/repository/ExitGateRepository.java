package com.parkingservice.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parkingservice.webapp.model.ExitGate;

public interface ExitGateRepository extends JpaRepository<ExitGate, Integer> {
	@Query("SELECT e FROM ExitGate e "
			+ "JOIN e.parkingLocation pl WHERE pl.locationId=:locationId ")
	public List<ExitGate> getExitGateList(@Param("locationId") int locationId);
}
