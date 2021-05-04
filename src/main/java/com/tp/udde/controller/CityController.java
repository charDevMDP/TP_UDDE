package com.tp.udde.controller;

import com.tp.udde.domain.City;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tp.udde.service.CityService;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    CityService cityService;

    // agrego uno
    @PostMapping
    public City addCity(@RequestBody City city) {
        return cityService.add(city);
    }

    // traigo todos
    @GetMapping
    public List<City> getAll() {
        return cityService.getAll();
    }

    // traigo uno
    @GetMapping("/{id}")
    public City getById(@PathVariable Integer id) {
        return cityService.getById(id);
    }

    // actualizo
    @PutMapping("/{id}")
    public City replaceCity(@PathVariable Integer id, @RequestBody City city) {
        return cityService.update(id, city);
    }

    // elimino uno
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        cityService.deleteById(id);
    }

}