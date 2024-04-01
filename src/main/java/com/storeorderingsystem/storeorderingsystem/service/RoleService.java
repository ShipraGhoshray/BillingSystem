package com.storeorderingsystem.storeorderingsystem.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.model.Role;

@Service
public interface RoleService{

	public Role addRole(long roleId, String name);
	public Iterable<Role> lookup();
	public Optional<Role> findById(long id);
	public long total();
	public void delete(Role role);
}
