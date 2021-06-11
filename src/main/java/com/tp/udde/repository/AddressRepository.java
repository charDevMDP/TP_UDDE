package com.tp.udde.repository;


import com.tp.udde.domain.Rate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query(value = "select * from addresses", nativeQuery = true)
    Page<Address> getAll(Pageable pageable);

    @Query(value = "select * from addresses WHERE id = ?1", nativeQuery = true)
    Page<Address> getByIdAddress(Integer id,Pageable pageable);

}