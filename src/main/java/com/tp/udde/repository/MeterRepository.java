package com.tp.udde.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.Meter;

@Repository
public interface MeterRepository extends JpaRepository<Meter, Integer> {

}