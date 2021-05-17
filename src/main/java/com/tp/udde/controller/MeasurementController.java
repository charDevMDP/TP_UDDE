package com.tp.udde.controller;


import com.tp.udde.domain.Measurement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import com.tp.udde.service.MeasurementService;

@RestController
@RequestMapping("/measurement")
public class MeasurementController {

    @Autowired
    MeasurementService measurementService;

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

    // traigo las mediciones de entre fechas
    @GetMapping("/data/{id}/{firstDate}/{secondDate}")
    public List<Measurement> getMeasurementBetweenDates(@PathVariable Integer user_id,
                                                        @PathVariable(value = "firstDate", required = true) @DateTimeFormat(pattern = "YYYY-MM-DD") String firstDate,
                                                        @PathVariable(value = "secondDate", required = true) @DateTimeFormat(pattern = "YYYY-MM-DD") String secondDate){
        return measurementService.getMeasurementByDates(user_id,firstDate,secondDate);
    }
}