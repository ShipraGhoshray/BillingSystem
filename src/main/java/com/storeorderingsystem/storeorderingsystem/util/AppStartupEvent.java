package com.storeorderingsystem.storeorderingsystem.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import com.storeorderingsystem.storeorderingsystem.repository.StoreUser;
import com.storeorderingsystem.storeorderingsystem.repository.StoreUserRepository;
import com.storeorderingsystem.storeorderingsystem.service.OrderService;

public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent>{

	@Autowired
	private OrderService billProcessor;
	
	private final StoreUserRepository storeUserRepository;
	
	public AppStartupEvent(StoreUserRepository storeUserRepository) {
		this.storeUserRepository = storeUserRepository;
	} 
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		// TODO Auto-generated method stub
		Iterable<StoreUser> storeUsers = this.storeUserRepository.findAll();
		storeUsers.forEach(System.out::println);
		
	}

}
