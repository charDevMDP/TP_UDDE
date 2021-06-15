package com.tp.udde.controller;


import com.tp.udde.domain.Measurement;
import com.tp.udde.domain.dto.MeasurementDto;
import com.tp.udde.projections.UserMeasurementConsumption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.tp.udde.projections.Consumption;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import com.tp.udde.service.MeasurementService;

@Slf4j
@Controller
public class MeasurementController {

    @Autowired
    MeasurementService measurementService;

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }


    public Measurement addMeasurement(MeasurementDto measurementDto) {
        Measurement measurement = new Measurement();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dataTime = measurementDto.getDate().replaceAll("T"," ");
        Date date = null;
        try {
            date = dt.parse(dataTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        measurement.setDate(date);
        measurement.setKwh(measurementDto.getValue());
        return measurementService.add(measurement);
    }
    /*
    // agrego uno
    @PostMapping
    public Measurement addMeasurement(@RequestBody Measurement measurement) {
        return measurementService.add(measurement);
    }

    // traigo todos
    @GetMapping
    public List<Measurement> getAll() {
        return measurementService.getAll();
    }

    // traigo uno
    @GetMapping("/{id}")
    public Measurement getById(@PathVariable Integer id) {
        return measurementService.getById(id);
    }

    // actualizo
    @PutMapping("/{id}")
    public Measurement replaceMeasurement(@PathVariable Integer id, @RequestBody Measurement measurement) {
        return measurementService.update(id, measurement);
    }

    // elimino uno
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        measurementService.deleteById(id);
    }

  */

    // lab.4 traigo consumo por rango por fechas (kwh y dinero en ese periodo)
    public Consumption getConsumption(Integer meter_id, LocalDate firstDate, LocalDate secondDate){
        return measurementService.getConsumption(meter_id,firstDate,secondDate);
    }

    // lab.5 traigo las mediciones de entre fechas
    public Page<Measurement> getMeasurementBetweenDates(Pageable pageable, Integer meter_id, LocalDate firstDate, LocalDate secondDate){
        return measurementService.getMeasurementByDates(pageable, meter_id,firstDate,secondDate);
    }

    // 6) Consulta de mediciones de un domicilio por rango de fechas
    public Page<Measurement> getMeasurementForDateForAddress(Pageable pageable, Integer idAddress, LocalDate firstDate, LocalDate secondDate){
        return measurementService.getMeasurementForDateForAddress(pageable, idAddress,firstDate,secondDate);
    }

    // 5) Consulta 10 clientes más consumidores en un rango de fechas.
    public Page<UserMeasurementConsumption> getUserForDateForConsumption(Pageable pageable, LocalDate firstDate, LocalDate secondDate){
        return measurementService.getUserForDateForConsumption(pageable,firstDate,secondDate);
    }

}