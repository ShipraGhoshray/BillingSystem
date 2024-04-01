package com.storeorderingsystem.storeorderingsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ROLES")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_ID")
    private int roleId;
    
	@Column(name = "ROLE_NAME")
    private String name;

	public Role() { }
	
	public Role(String name) {
	    this.name = name;
	}
	
	public Role(int roleId, String name) {
		this.roleId = roleId;
	    this.name = name;
	}
	
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