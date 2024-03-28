package com.storeorderingsystem.storeorderingsystem.authentication.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.authentication.model.Role;
import com.storeorderingsystem.storeorderingsystem.authentication.model.User;

@Service
public interface RoleService{

	public Role addRole(long roleId, String name, List<User> users);
	public Iterable<Role> lookup();
	public Optional<Role> findById(long id);
	public long total();
	public void delete(Role role);
}
