package com.parkingservice.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parkingservice.webapp.model.ParkingTariff;

public interface ParkingTariffRepository extends JpaRepository<ParkingTariff, Integer> {
	@Modifying
	@Query("DELETE FROM ParkingTariff pt WHERE pt.locationId=:locationId AND pt.vehicleTypeId=:vehicleTypeId")
	public void deleteTariffData(@Param("locationId") int locationId, @Param("vehicleTypeId") int vehicleTypeId);
}
