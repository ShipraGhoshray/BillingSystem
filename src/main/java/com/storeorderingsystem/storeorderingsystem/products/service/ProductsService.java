package com.storeorderingsystem.storeorderingsystem.products.service;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.products.model.Products;

@Service
public interface ProductsService{

	public Products createProducts(String name, int price, String type);
	public Iterable<Products> lookup();
	public long total();  
	public Products save(Products product);  
    public Optional<Products> findById(long productId, String name);
}
