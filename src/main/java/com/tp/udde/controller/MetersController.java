package com.tp.udde.controller;

import com.tp.udde.domain.Meters;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import com.tp.udde.service.MetersService;

@RestController
@RequestMapping("/measurer")
public class MetersController {

    @Autowired
    MetersService metersService;

    // agrego uno
    @PostMapping
    public Meters addMeasurer(@RequestBody Meters meters) {
        return metersService.add(meters);
    }

    // traigo todos
    @GetMapping
    public List<Meters> getAll() {
        return metersService.getAll();
    }

    // traigo uno
    @GetMapping("/{id}")
    public Meters getById(@PathVariable Integer id) {
        return metersService.getById(id);
    }

    // actualizo
    @PutMapping("/{id}")
    public Meters replaceMeasurer(@PathVariable Integer id, @RequestBody Meters meters) {
        return metersService.update(id, meters);
    }

    // elimino uno
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        metersService.deleteById(id);
    }

}