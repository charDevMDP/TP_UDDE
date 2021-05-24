package com.tp.udde.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.Measurement;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    @Query(value = "SELECT mt.date as DATA, mt.kwh as KWH FROM  users us "+
            "inner join addresses ad on ad.id_user = us.id" +
            " inner join meters me on me.id_address = ad.id"+
            "inner join meters_for_measurement mm on me.id = mm.id_meters"+
            "inner join measurements mt on mm.id_meters = mt.id WHERE us.id = ?1;", nativeQuery = true)
    List<Measurement> getMeasurementForDate(Integer userId, String startDate, String endDate);
}