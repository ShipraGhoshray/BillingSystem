package com.storeorderingsystem.storeorderingsystem.util;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import com.storeorderingsystem.storeorderingsystem.model.User;
import com.storeorderingsystem.storeorderingsystem.repository.UserRepository;

public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent>{

	//@Autowired
	//private BillProcessingService billProcessor;
	
	private final UserRepository storeUserRepository;
	
	public AppStartupEvent(UserRepository storeUserRepository) {
		this.storeUserRepository = storeUserRepository;
	} 
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		// TODO Auto-generated method stub
		Iterable<User> storeUsers = this.storeUserRepository.findAll();
		storeUsers.forEach(System.out::println);
		
	}

}
