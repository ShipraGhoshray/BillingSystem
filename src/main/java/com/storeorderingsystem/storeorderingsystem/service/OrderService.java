package com.storeorderingsystem.storeorderingsystem.service;

import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.model.Bill;
import com.storeorderingsystem.storeorderingsystem.model.BillAmount;

@Service
public interface OrderService{
	
	public BillAmount processBill(Bill billInfo);	
}
