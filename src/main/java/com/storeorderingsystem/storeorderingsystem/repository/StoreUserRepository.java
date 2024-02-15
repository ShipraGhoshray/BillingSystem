package com.storeorderingsystem.storeorderingsystem.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface StoreUserRepository extends CrudRepository<StoreUser, Long>{
	//public Optional<StoreUser> findById(Long id);
}
