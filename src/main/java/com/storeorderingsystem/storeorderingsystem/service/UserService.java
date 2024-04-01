package com.storeorderingsystem.storeorderingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.model.Role;
import com.storeorderingsystem.storeorderingsystem.model.User;

@Service
public interface UserService{

	public User createStoreUser(long id, List<Role> roles, String firstName, String lastName, String username, String password, String emailId, long phoneNumber);
	public Iterable<User> lookup();
	public Optional<User> findById(long id);
	public long total();
	public void delete(User user);
}
