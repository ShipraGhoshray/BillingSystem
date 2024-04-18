package com.storeorderingsystem.storeorderingsystem.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.dto.UserRequest;
import com.storeorderingsystem.storeorderingsystem.model.Role;
import com.storeorderingsystem.storeorderingsystem.model.User;

@Service
public interface UserService{

	public User createStoreUser(String username, String password, List<Role> roles, String firstName, String lastName,  
			String emailId, long phoneNumber, Date joiningDate);
	public void save(UserRequest user);
	public Iterable<User> lookup();
	public Optional<User> findById(long id);
	public Optional<User> findByEmail(String email);
	public long total();
	public void delete(User user);
}