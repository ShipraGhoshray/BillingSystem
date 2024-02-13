package com.storeorderingsystem.storeorderingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.storeorderingsystem.storeorderingsystem.util.DateUtils;

@SpringBootApplication
public class StoreOrderingSystemApplication {

	public static void main(String[] args) {
		//DateUtils.isDateGreatThanTwoYears(DateUtils.)
		SpringApplication.run(StoreOrderingSystemApplication.class, args);
	}

}
