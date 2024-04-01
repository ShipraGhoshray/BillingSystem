package com.storeorderingsystem.storeorderingsystem.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.model.Role;
import com.storeorderingsystem.storeorderingsystem.model.User;
import com.storeorderingsystem.storeorderingsystem.repository.UserRepository;
import com.storeorderingsystem.storeorderingsystem.service.UserService;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}

