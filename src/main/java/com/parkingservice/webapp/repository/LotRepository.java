package com.parkingservice.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parkingservice.webapp.model.LOT;

public interface LotRepository extends JpaRepository<LOT, Integer> {
	@Query("SELECT l FROM LOT l "
			 + "WHERE l.createdBy=:userId AND "
			 + "l.deviceTypeId=3 AND DATE(l.createdTime)=CURRENT_DATE() ORDER BY l.createdTime desc")
		public List<LOT> getPOSLOTList(@Param("userId") int userId);
	
	public LOT findByLotId(int lotId);
}
