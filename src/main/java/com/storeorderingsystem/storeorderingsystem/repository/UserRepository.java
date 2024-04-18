package com.storeorderingsystem.storeorderingsystem.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.storeorderingsystem.storeorderingsystem.model.User;

public interface UserRepository extends CrudRepository<User, Long>{

	public Optional<User> findByUsername(String username);
	public Optional<User> findByEmailId(String emailId);
}
