package com.tp.udde.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
}