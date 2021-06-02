package com.tp.udde.repository;

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
    @Query(value = "SELECT mt.date as DATA, mt.kwh as KWH FROM  users us "+
            "inner join addresses ad on ad.id_user = us.id" +
            "inner join meters me on me.id_address = ad.id"+
            "inner join meters_for_measurement mm on mm.id_meters = me.id"+
            "inner join measurements mt on mt.id_meters = mm.id"+
            "WHERE us.id = ?1 BETWEEN ?2 AND ?3 ;", nativeQuery = true)
    List<Measurement> getMeasurementForDate(Integer userId, String startDate, String endDate);

    // lab.4
    @Query(value = "SELECT SUM(mt.kwh) as totalkwh, SUM(price) AS pricetotal " +
            "FROM  users us "+
            "inner join addresses ad on ad.id_user = us.id" +
            "inner join meters me on me.id_address = ad.id"+
            "inner join meters_for_measurement mm on mm.id_meters = me.id"+
            "inner join measurements mt on mt.id_meters = mm.id"+
            "WHERE us.id = ?1 BETWEEN ?2 AND ?3", nativeQuery = true)
    Map<Float, Float> getConsumptionForDate(Integer user_id, String firstDate, String secondDate);
}