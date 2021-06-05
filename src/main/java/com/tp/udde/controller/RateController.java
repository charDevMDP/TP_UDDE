package com.tp.udde.controller;

import com.tp.udde.domain.Rate;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.tp.udde.service.RateService;

@Controller
public class RateController {

    @Autowired
    private RateService rateService;

    // traigo todos
    public List<Rate> getRates() { return this.rateService.getAll();}

    // agrego uno
    public Rate addRate(@RequestBody Rate rate) {return rateService.add(rate);}

    // elimino uno
    public void deleteByIdRate(Integer id) {rateService.deleteByIdRate(id);
    }

    // actualizo
    public Rate replaceRate(@PathVariable Integer id, @RequestBody Rate rate) {return rateService.update(id, rate);}

/*

    // traigo uno
    @GetMapping("/{id}")
    public Rate getById(@PathVariable Integer id) {
        return rateService.getById(id);
    }

*/
}