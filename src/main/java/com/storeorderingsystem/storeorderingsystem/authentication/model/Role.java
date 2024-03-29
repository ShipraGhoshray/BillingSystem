package com.storeorderingsystem.storeorderingsystem.authentication.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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

	/*@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;*/

	public Role() { }
	
	public Role(int roleId, String name , List<User> users) {
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