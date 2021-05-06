package com.tp.udde.controller;

import com.tp.udde.domain.Measurer;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tp.udde.service.MeasurerService;

@RestController
@RequestMapping("/measurer")
public class MeasurerController {

    @Autowired
    MeasurerService measurerService;

    // agrego uno
    @PostMapping
    public Measurer addMeasurer(@RequestBody Measurer measurer) {
        return measurerService.add(measurer);
    }

    // traigo todos
    @GetMapping
    public List<Measurer> getAll() {
        return measurerService.getAll();
    }

    // traigo uno
    @GetMapping("/{id}")
    public Measurer getById(@PathVariable Integer id) {
        return measurerService.getById(id);
    }

    // actualizo
    @PutMapping("/{id}")
    public Measurer replaceMeasurer(@PathVariable Integer id, @RequestBody Measurer measurer) {
        return measurerService.update(id, measurer);
    }

    // elimino uno
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        measurerService.deleteById(id);
    }

}