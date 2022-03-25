package com.parkingservice.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkingservice.webapp.model.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Integer> {

}
