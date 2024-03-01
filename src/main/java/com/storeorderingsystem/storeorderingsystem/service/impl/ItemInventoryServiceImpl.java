package com.storeorderingsystem.storeorderingsystem.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.repository.ItemQuantity;
import com.storeorderingsystem.storeorderingsystem.repository.ItemQuantityRepository;
import com.storeorderingsystem.storeorderingsystem.service.ItemInventoryService;

@Service
public class ItemInventoryServiceImpl implements ItemInventoryService{
    private ItemQuantityRepository itemQuantityRepository;

    public ItemInventoryServiceImpl(ItemQuantityRepository itemQuantityRepository) {
        this.itemQuantityRepository = itemQuantityRepository;
    }

    @Override
    public ItemQuantity createItemInventory(long itemId, String name, int price, int quantity, String type) {
        return itemQuantityRepository.findById(itemId).orElse(
        		itemQuantityRepository.save(new ItemQuantity(itemId, name, price, quantity, type)));   
    }

    @Override
    public Iterable<ItemQuantity> lookup(){
        return itemQuantityRepository.findAll();
    }

    @Override
    public long total() {
        return itemQuantityRepository.count();
    }
    
    @Override
	public ItemQuantity save(ItemQuantity item) {
    	return itemQuantityRepository.save(item);
    }
    
    @Override
    public Optional<ItemQuantity> findById(long id, String name){
    	return itemQuantityRepository.findById(id);
    }
}

