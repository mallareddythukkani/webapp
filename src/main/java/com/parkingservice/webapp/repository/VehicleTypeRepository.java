package com.parkingservice.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkingservice.webapp.model.VehicleType;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Short> {

}
