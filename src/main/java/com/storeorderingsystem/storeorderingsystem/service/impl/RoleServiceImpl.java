package com.storeorderingsystem.storeorderingsystem.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.model.Role;
import com.storeorderingsystem.storeorderingsystem.model.User;
import com.storeorderingsystem.storeorderingsystem.repository.RoleRepository;
import com.storeorderingsystem.storeorderingsystem.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Iterable<Role> lookup(){
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(long id){
    	return roleRepository.findById(id);
    }
    
    @Override
    public long total() {
        return roleRepository.count();
    }

	@Override
	public Role addRole(long roleId, String name) {
		return roleRepository.findById(roleId).orElse(roleRepository.save(new Role(Integer.valueOf(String.valueOf(roleId)), name)));
	}

	@Override
	public void delete(Role user){
		roleRepository.delete(user);
	}
}

