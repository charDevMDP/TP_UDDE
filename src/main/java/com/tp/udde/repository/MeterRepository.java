package com.tp.udde.repository;

import com.tp.udde.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.Meter;

@Repository
public interface MeterRepository extends JpaRepository<Meter, Integer> {

    @Query(value = "select * from meters m where m.number = ?1 and m.password = ?2", nativeQuery = true)
    Meter getByMeterNumberAndPass(String number, String password);

}