package com.storeorderingsystem.storeorderingsystem.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="store_user", schema = "onlinestore")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private long userId;
	
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_roles", schema = "onlinestore",
    	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private List<Role> roles = new ArrayList<>();
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email_address")
	private String emailId;
	
	@Column(name = "phone_number")
	private long phoneNumber;

	@Column(name = "joining_date")
	private Date joiningDate;
	
	public User() {
	}
	
	public User(List<Role> roles, String firstName, String lastName, String username, String password, String emailId, 
			long phoneNumber, Date joiningDate) {
	    this.roles = roles;
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.username = username;
	    this.password = password;
	    this.emailId = emailId;
	    this.phoneNumber = phoneNumber;
	    this.joiningDate = joiningDate;
	}
	
	public User(long userId, List<Role> roles, String firstName, String lastName, String username, String password, String emailId, 
			long phoneNumber, Date joiningDate) {
		this.userId = userId;
	    this.roles = roles;
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.username = username;
	    this.password = password;
	    this.emailId = emailId;
	    this.phoneNumber = phoneNumber;
	    this.joiningDate = joiningDate;
	    		;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

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
	
	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	@Override
	public String toString() {
		return "StoreUser{" +
				"userId='" + userId + '\'' +
				", roles='" + roles + '\'' +
				", username='" + username + '\'' +
				//", password='" + password + '\'' +
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
		User that = (User) o;
		return Objects.equals(userId, that.userId) && 
				Objects.equals(roles, that.roles) &&
				Objects.equals(firstName, that.firstName) &&
				Objects.equals(lastName, that.lastName) &&
				Objects.equals(username, that.username) &&
				Objects.equals(emailId, that.emailId) &&
				Objects.equals(phoneNumber, that.phoneNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, roles, firstName, lastName, username, emailId, phoneNumber);
	}
}
