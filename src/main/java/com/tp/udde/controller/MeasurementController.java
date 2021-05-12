package com.tp.udde.controller;

import com.tp.udde.domain.Invoice;
import com.tp.udde.domain.Measurement;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
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
    @GetMapping("/data")
    public List<Measurement> getMeasurementBetweenDates(@RequestParam Integer user_id, @RequestParam LocalDate initialDate, @RequestParam LocalDate endDate){
        return measurementService.getMeasurementByDates(user_id, initialDate,endDate);
    }
}