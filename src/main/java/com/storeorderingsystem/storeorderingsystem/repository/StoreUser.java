package com.storeorderingsystem.storeorderingsystem.repository;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="STORE_USER")
public class StoreUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "STORE_USER_ID")
	private long id;
	
	@Column(name = "STORE_USER_TYPE")
	private String userType;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "EMAIL_ADDRESS")
	private String emailId;
	
	@Column(name = "PHONE_NUMBER")
	private long phoneNumber;

	@Column(name = "JOINING_DATE")
	private String joiningDate;
	
	public StoreUser() {
	}

	public StoreUser(long id, String userType, String firstName, String lastName, String emailId, long phoneNumber) {
		this.id = id;
	    this.userType = userType;
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.emailId = emailId;
	    this.phoneNumber = phoneNumber;
	}
	
	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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
	
	@Override
	public String toString() {
		return "StoreUser{" +
				"id='" + id + '\'' +
				", userType='" + userType + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", emailId='" + emailId + '\'' +
				", phoneNumber'" + phoneNumber + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) 
			return true;
		if (o == null || getClass() != o.getClass()) 
			return false;
		StoreUser that = (StoreUser) o;
		return Objects.equals(id, that.id) && 
				Objects.equals(userType, that.userType)&&
				Objects.equals(firstName, that.firstName)&&
				Objects.equals(lastName, that.lastName)&&
				Objects.equals(emailId, that.emailId)&&
				Objects.equals(phoneNumber, that.phoneNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, userType, firstName, lastName, emailId, phoneNumber);
	}
}
