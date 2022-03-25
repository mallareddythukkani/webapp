package com.parkingservice.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkingservice.webapp.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
