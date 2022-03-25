package com.parkingservice.webapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.parkingservice.webapp.model.UserLog;

public interface UserLogRepository extends CrudRepository<UserLog, Integer> {

}
