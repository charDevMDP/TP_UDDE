package com.tp.udde.repository;

import com.tp.udde.domain.MetersForMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetersForMeasurementRepository extends JpaRepository<MetersForMeasurement, Integer> {
}
