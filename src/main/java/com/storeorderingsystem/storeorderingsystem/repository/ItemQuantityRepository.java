package com.storeorderingsystem.storeorderingsystem.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ItemQuantityRepository extends CrudRepository<ItemQuantity, Long>{
	public ItemQuantity save(ItemQuantity item);
}