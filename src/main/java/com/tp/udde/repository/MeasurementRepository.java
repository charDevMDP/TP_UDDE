package com.tp.udde.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.Measurement;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    @Query(value = "SELECT FROM * MEASUREMENT", nativeQuery = true) // TODO ver query
    List<Measurement> getMeasurementForDate(Integer userId, LocalDate startDate, LocalDate endDate);
}