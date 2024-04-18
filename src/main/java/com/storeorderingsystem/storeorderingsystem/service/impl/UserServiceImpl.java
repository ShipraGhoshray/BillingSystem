package com.storeorderingsystem.storeorderingsystem.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.dto.UserRequest;
import com.storeorderingsystem.storeorderingsystem.model.Role;
import com.storeorderingsystem.storeorderingsystem.model.User;
import com.storeorderingsystem.storeorderingsystem.repository.UserRepository;
import com.storeorderingsystem.storeorderingsystem.service.UserService;
import com.storeorderingsystem.storeorderingsystem.util.Constants;
import com.storeorderingsystem.storeorderingsystem.util.DateUtils;

@Service
public class UserServiceImpl implements UserService{
	
	private final RoleServiceImpl roleService;
    private final UserRepository userRepository;
	final private PasswordEncoder passwordEncoder;

    public UserServiceImpl(RoleServiceImpl roleService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
    	this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Iterable<User> lookup(){
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(long id){
    	return userRepository.findById(id);
    }
    
    @Override
    public long total() {
        return userRepository.count();
    }

	@Override
	public User createStoreUser(String username, String password, List<Role> roles, String firstName, String lastName,  String emailId, 
			long phoneNumber,Date joiningDate) {
        return userRepository.findByUsername(username).orElse(
        	userRepository.save(new User(roles, firstName, lastName, username, password, emailId, phoneNumber, joiningDate)));
	}

	@Override
	public void delete(User user){
		userRepository.delete(user);
	}

	@Override
	public void save(UserRequest userReq) {
		
		User user = new User();
		user.setFirstName(userReq.getFirstName());
		user.setLastName(userReq.getLastName());
		user.setUsername(userReq.getUsername());
		user.setPassword(passwordEncoder.encode(userReq.getPassword())); 
		user.setEmailId(userReq.getEmailId());
		user.setPhoneNumber(userReq.getPhoneNumber());
		if(userReq.getRoles() != null) {
			user.setRoles(roleService.getRolesByName(Constants.USER_ROLE_USER));
		}
		user.setJoiningDate(DateUtils.getCurrentDate());
		userRepository.save(user);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return  userRepository.findByEmailId(email);
	}
}

