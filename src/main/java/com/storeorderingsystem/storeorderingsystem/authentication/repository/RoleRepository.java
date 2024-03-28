package com.storeorderingsystem.storeorderingsystem.authentication.repository;

import org.springframework.data.repository.CrudRepository;

import com.storeorderingsystem.storeorderingsystem.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long>{

	//public Optional<User> findByUsername(String username);
}
