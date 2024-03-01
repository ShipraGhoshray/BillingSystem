package com.storeorderingsystem.storeorderingsystem.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.repository.StoreUser;
import com.storeorderingsystem.storeorderingsystem.repository.StoreUserRepository;
import com.storeorderingsystem.storeorderingsystem.service.UserService;

@Service
public class UserServiceImpl implements UserService{
    private StoreUserRepository storeUserRepository;

    public UserServiceImpl(StoreUserRepository storeUserRepository) {
        this.storeUserRepository = storeUserRepository;
    }

    @Override
    public Iterable<StoreUser> lookup(){
        return storeUserRepository.findAll();
    }

    @Override
    public Optional<StoreUser> findById(long id){
    	return storeUserRepository.findById(id);
    }
    
    @Override
    public long total() {
        return storeUserRepository.count();
    }

	@Override
	public StoreUser createStoreUser(long id, String userType, String firstName, String lastName, String emailId, long phoneNumber) {
        return storeUserRepository.findById(id).orElse(
        		storeUserRepository.save(new StoreUser(id, userType, firstName, lastName, emailId, phoneNumber)));   
	}

	@Override
	public void delete(StoreUser user){
		storeUserRepository.delete(user);
	}
}

