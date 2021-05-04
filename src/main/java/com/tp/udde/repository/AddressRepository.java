package com.tp.udde.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}