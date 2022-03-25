package com.parkingservice.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkingservice.webapp.model.ParkingLocation;

public interface ParkingLocationRepository extends JpaRepository<ParkingLocation, Integer> {

}
