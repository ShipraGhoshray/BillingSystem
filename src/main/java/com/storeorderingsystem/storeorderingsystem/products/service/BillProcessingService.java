package com.storeorderingsystem.storeorderingsystem.products.service;

import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.products.dto.Bill;
import com.storeorderingsystem.storeorderingsystem.products.dto.BillAmount;

@Service
public interface BillProcessingService{
	
	public BillAmount processBill(Bill billInfo);	
	public Iterable<com.storeorderingsystem.storeorderingsystem.products.model.Bill> lookup();
	public long total();  
}
