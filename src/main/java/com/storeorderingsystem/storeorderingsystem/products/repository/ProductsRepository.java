package com.storeorderingsystem.storeorderingsystem.products.repository;

import org.springframework.data.repository.CrudRepository;

import com.storeorderingsystem.storeorderingsystem.products.model.ItemQuantity;

//@RepositoryRest	
public interface ProductsRepository extends CrudRepository<ItemQuantity, Long>{
}