package com.tp.udde.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.Measurement;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    @Query(value = "SELECT FROM * USER u inner join ADDRESS a on u.id = a.id_user "+
            "inner join METERS m on a.id = m.id_address"+
            "inner join METERS_FOR_MEASUREMENT mm on m.id = mm.id_meters" +
            "inner join MEASUREMENT mt on mm.id = mt.id_meters_for_measurement" +
            "WHERE u.id = ?1", nativeQuery = true) // TODO ver query
    List<Measurement> getMeasurementForDate(Integer userId, LocalDate startDate, LocalDate endDate);
}