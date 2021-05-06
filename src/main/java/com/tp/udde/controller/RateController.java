package com.tp.udde.controller;

import com.tp.udde.domain.Rate;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tp.udde.service.RateService;

@RestController
@RequestMapping("/rate")
public class RateController {

    @Autowired
    RateService rateService;

    // agrego uno
    @PostMapping
    public Rate addRate(@RequestBody Rate rate) {
        return rateService.add(rate);
    }

    // traigo todos
    @GetMapping
    public List<Rate> getAll() {
        return rateService.getAll();
    }

    // traigo uno
    @GetMapping("/{id}")
    public Rate getById(@PathVariable Integer id) {
        return rateService.getById(id);
    }

    // actualizo
    @PutMapping("/{id}")
    public Rate replaceRate(@PathVariable Integer id, @RequestBody Rate rate) {
        return rateService.update(id, rate);
    }

    // elimino uno
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        rateService.deleteById(id);
    }

}