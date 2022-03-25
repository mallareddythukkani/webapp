package com.parkingservice.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkingservice.webapp.model.PtExemptData;

public interface PtExemptDataRepository extends JpaRepository<PtExemptData, Long> {

}
