package com.storeorderingsystem.storeorderingsystem.products.service;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.products.repository.ItemQuantity;

@Service
public interface ProductsService{

	public ItemQuantity createItemInventory(long itemId, String name, int price, int quantity, String type);
	public Iterable<ItemQuantity> lookup();
	public long total();  
	public ItemQuantity save(ItemQuantity item);  
    public Optional<ItemQuantity> findById(long id, String name);
}
