package com.rk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rk.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
