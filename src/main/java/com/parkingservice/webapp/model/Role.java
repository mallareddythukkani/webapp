package com.parkingservice.webapp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Role {
	@Id
	private int roleId;
	private String name;
	private int status;
	
	public Role() {
		
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", name=" + name + ", status=" + status + "]";
	}
	
	
}
