package com.tp.udde.controller;

import com.tp.udde.domain.Measurement;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
}