package com.storeorderingsystem.storeorderingsystem.dto;

import java.util.List;

import com.storeorderingsystem.storeorderingsystem.model.Role;

public class UserRequest {
	
	private List<RoleRequest> roles;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String emailId;
	private long phoneNumber;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<RoleRequest> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleRequest> roles) {
		this.roles = roles;
	}
}
