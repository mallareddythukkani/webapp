package com.parkingservice.webapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parkingservice.webapp.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
	@Query("SELECT u FROM User u "
			 + "JOIN u.role r WHERE r.roleId IN (:roleStr) ORDER BY u.createdTime desc")
	public List<User> getUserList(@Param("roleStr") int[] roleStr);
	@Query("SELECT u FROM User u "
			 + "JOIN u.role r WHERE u.locationId=:locationId AND r.roleId = 5 AND u.status=1  ORDER BY u.createdTime desc")
	public List<User> getCashierList(@Param("locationId") int locationId);
}
