package com.storeorderingsystem.storeorderingsystem.products.repository;

import org.springframework.data.repository.CrudRepository;

//@RepositoryRest	
public interface ProductsRepository extends CrudRepository<ItemQuantity, Long>{
}