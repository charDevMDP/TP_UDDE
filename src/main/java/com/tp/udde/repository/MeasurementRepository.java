package com.tp.udde.repository;

import com.tp.udde.projections.Consumption;
import com.tp.udde.projections.UserMeasurementConsumption;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.Measurement;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
            "(SELECT SUM(r.price) from meters me " +
            "inner join rates r on me.id_rate = r.id " +
            "where me.number = ?1 ) * SUM(mt.kwh) AS priceTotal " +
            "FROM  meters me "+
            "inner join meters_for_measurement mm on mm.id_meters = me.id "+
            "inner join measurements mt on mt.id = mm.id "+
            "WHERE mt.date BETWEEN ?2 AND ?3 AND me.number = ?1", nativeQuery = true)
    Consumption getConsumptionForDate(Integer meter_id, Date firstDate, Date secondDate);

    // lab.6
    @Query(value = "SELECT mea.* FROM addresses ad "+
            "INNER JOIN meters mt ON mt.id_address = ad.id INNER JOIN meters_for_measurement mfm ON mfm.id_meters = mt.id " +
            "INNER JOIN measurements mea ON mea.id = mfm.id_measurement WHERE mea.date BETWEEN ?2 AND ?3 AND ad.id = ?1", nativeQuery = true)
    List<Measurement> getMeasurementForDateForAddress(Integer idAddress, LocalDate startDate, LocalDate endDate);

    //lab.5
    @Query(value = " SELECT c.*, SUM(mea.kwh) AS Consumption, ad.name AS NameAddress, ad.number AS NumberAddress, ad.department AS DepartmentAddress FROM users c" + " INNER JOIN addresses ad ON ad.id_user = c.id" +
            " INNER JOIN meters mt ON mt.id_address = ad.id" +
            " INNER JOIN meters_for_measurement mfm ON mfm.id_meters = mt.id " +
            " INNER JOIN measurements mea ON mea.id = mfm.id_measurement" +
            " WHERE mea.date BETWEEN ?1 AND ?2 AND c.user_type = \"CLIENT\" GROUP BY c.id " +
            " ORDER BY SUM(mea.kwh) DESC LIMIT 10;", nativeQuery = true)
    List<UserMeasurementConsumption> getUserForDateForConsumption(LocalDate startDate, LocalDate endDate);



}