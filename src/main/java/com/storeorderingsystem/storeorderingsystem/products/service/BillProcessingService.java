package com.storeorderingsystem.storeorderingsystem.products.service;

import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.products.model.Bill;
import com.storeorderingsystem.storeorderingsystem.products.model.BillAmount;

@Service
public interface BillProcessingService{
	
	public BillAmount processBill(Bill billInfo);	
	public Iterable<com.storeorderingsystem.storeorderingsystem.products.repository.Bill> lookup();
	public long total();  
}
