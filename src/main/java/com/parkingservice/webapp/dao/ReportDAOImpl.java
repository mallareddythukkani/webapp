package com.parkingservice.webapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ReportDAOImpl implements ReportDAO {
	
	//define entity manager
	@Autowired
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> executeNativeQuery(String qry) {
		Query q2 = entityManager.createNativeQuery(qry);
		return q2.getResultList();
	}

}
