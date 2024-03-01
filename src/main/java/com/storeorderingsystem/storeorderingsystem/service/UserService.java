package com.storeorderingsystem.storeorderingsystem.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.repository.StoreUser;

@Service
public interface UserService{

	public StoreUser createStoreUser(long id, String userType, String firstName, String lastName, String emailId, long phoneNumber);
	public Iterable<StoreUser> lookup();
	public Optional<StoreUser> findById(long id);
	public long total();
	public void delete(StoreUser user);
}
