package com.storeorderingsystem.storeorderingsystem.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.storeorderingsystem.storeorderingsystem.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long>{
	public Optional<Role> findByName(String name);
}
