package com.storeorderingsystem.storeorderingsystem.products.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.storeorderingsystem.storeorderingsystem.products.model.Products;

public interface ProductsRepository extends CrudRepository<Products, Long>{
	
	public Optional<Products> findByName(String productName);
}