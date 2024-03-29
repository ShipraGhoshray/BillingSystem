package com.storeorderingsystem.storeorderingsystem.products.service;

import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.products.dto.BillDto;
import com.storeorderingsystem.storeorderingsystem.products.dto.BillAmountDto;

@Service
public interface BillProcessingService{
	
	public BillAmountDto processBill(BillDto billInfo);	
	public Iterable<com.storeorderingsystem.storeorderingsystem.products.model.Bill> lookup();
	public long total();  
}
