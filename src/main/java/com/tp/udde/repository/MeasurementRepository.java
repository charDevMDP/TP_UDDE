package com.tp.udde.repository;

import com.tp.udde.projections.Consumption;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.Measurement;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    // lab.5
    @Query(value = "SELECT * FROM meters me "+
            "inner join meters_for_measurement mm on mm.id_meters = me.id "+
            "inner join measurements mt on mt.id = mm.id "+
            "WHERE  mt.date BETWEEN ?2 AND ?3 AND me.number = ?1 ", nativeQuery = true)
    List<Measurement> getMeasurementForDate(Integer idMeter, LocalDate startDate, LocalDate endDate);

    // lab.4
    @Query(value = "SELECT SUM(mt.kwh) as totalKwh, " +
            //"(SELECT SUM(r.price) from meters me inner join rate r  where me.id_rate = r.id  ) AS priceTotal " + ESTE
            //"(SELECT SUM(r.price) from rate r where r.id = meters.id_rate  ) AS priceTotal " +                    O ESTE
            "FROM  meters me"+
            "inner join meters_for_measurement mm on mm.id_meters = me.id"+
            "inner join measurements mt on mt.id_meters = mm.id"+
            "WHERE me.number = ?1 BETWEEN ?2 AND ?3", nativeQuery = true)
    Consumption getConsumptionForDate(Integer meter_id, LocalDate firstDate, LocalDate secondDate);
}