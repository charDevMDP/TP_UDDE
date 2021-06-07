package com.tp.udde.controller;


import com.tp.udde.domain.Measurement;
import com.tp.udde.projections.Consumption;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import com.tp.udde.service.MeasurementService;

@Controller
public class MeasurementController {

    @Autowired
    MeasurementService measurementService;
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
    public Consumption getConsumption(Integer meter_id, Date firstDate, Date secondDate){
        return measurementService.getConsumption(meter_id,firstDate,secondDate);
    }

    // lab.5 traigo las mediciones de entre fechas
    public List<Measurement> getMeasurementBetweenDates(Integer meter_id, LocalDate firstDate, LocalDate secondDate){
        return measurementService.getMeasurementByDates(meter_id,firstDate,secondDate);
    }


}