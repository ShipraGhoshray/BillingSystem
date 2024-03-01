package com.storeorderingsystem.storeorderingsystem.service;

import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.model.Bill;
import com.storeorderingsystem.storeorderingsystem.model.BillAmount;

@Service
public interface BillProcessingService{
	
	public BillAmount processBill(Bill billInfo);	
	public Iterable<com.storeorderingsystem.storeorderingsystem.repository.Bill> lookup();
	public long total();  
}
