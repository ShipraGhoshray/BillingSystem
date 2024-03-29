package com.storeorderingsystem.storeorderingsystem.products.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.products.model.Products;
import com.storeorderingsystem.storeorderingsystem.products.repository.ProductsRepository;
import com.storeorderingsystem.storeorderingsystem.products.service.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService{
    private ProductsRepository productsRepository;

    public ProductsServiceImpl(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public Products createProducts(long productId, String name, int price, int quantity, String type) {
        return productsRepository.findById(productId).orElse(
        		productsRepository.save(new Products(productId, name, price, quantity, type)));   
    }

    @Override
    public Iterable<Products> lookup(){
        return productsRepository.findAll();
    }

    @Override
    public long total() {
        return productsRepository.count();
    }
    
    @Override
	public Products save(Products product) {
    	return productsRepository.save(product);
    }
    
    @Override
    public Optional<Products> findById(long productId, String name){
    	return productsRepository.findById(productId);
    }
}