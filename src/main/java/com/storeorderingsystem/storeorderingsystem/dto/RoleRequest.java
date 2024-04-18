package com.storeorderingsystem.storeorderingsystem.dto;

public class RoleRequest {
    private int roleId;
    private String name;

	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int role_id) {
		this.roleId = role_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}   
}