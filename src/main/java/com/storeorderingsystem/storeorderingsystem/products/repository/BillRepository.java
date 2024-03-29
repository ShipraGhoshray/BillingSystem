package com.storeorderingsystem.storeorderingsystem.products.repository;

import org.springframework.data.repository.CrudRepository;

import com.storeorderingsystem.storeorderingsystem.products.model.Bill;


public interface BillRepository extends CrudRepository<Bill, Long>{
}
