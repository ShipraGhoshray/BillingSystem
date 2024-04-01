package com.storeorderingsystem.storeorderingsystem.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.model.Role;
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
		return roleRepository.findByName(name).orElse(roleRepository.save(new Role(name)));
	}

	@Override
	public void delete(Role user){
		roleRepository.delete(user);
	}
	
	@Override
	public List<Role> getRolesByName(String... roleNames){
		
		List<Role> roles = new ArrayList<Role>();
		Iterable<Role> repoRoles = roleRepository.findAll();
		for(Role role : repoRoles) {
			for(String roleName : roleNames) {
				if(role.getName().equalsIgnoreCase(roleName)) {
					roles.add(role);
				}
			}
		}
		return roles;
	}
}

