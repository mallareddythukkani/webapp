package com.parkingservice.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkingservice.webapp.model.UserEmployee;

public interface UserEmployeeRepository extends JpaRepository<UserEmployee, Integer> {
	 public UserEmployee findByUserId(int userId);
}
