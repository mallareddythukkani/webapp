package com.parkingservice.webapp.dao;

import java.util.List;

public interface ReportDAO {
	public List<Object[]> executeNativeQuery(String qry);
}
