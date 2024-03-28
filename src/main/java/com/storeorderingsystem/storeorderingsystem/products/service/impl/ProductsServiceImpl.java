package com.storeorderingsystem.storeorderingsystem.products.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.products.repository.ItemQuantity;
import com.storeorderingsystem.storeorderingsystem.products.repository.ProductsRepository;
import com.storeorderingsystem.storeorderingsystem.products.service.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService{
    private ProductsRepository itemQuantityRepository;

    public ProductsServiceImpl(ProductsRepository itemQuantityRepository) {
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

