package com.storeorderingsystem.storeorderingsystem.products.repository;

import org.springframework.data.repository.CrudRepository;

import com.storeorderingsystem.storeorderingsystem.products.model.Products;

//@RepositoryRest	
public interface ProductsRepository extends CrudRepository<Products, Long>{
}