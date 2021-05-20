package com.tp.udde.controller;

import com.tp.udde.domain.Brand;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.tp.udde.service.BrandService;

@Controller
public class BrandController {

    @Autowired
    BrandService brandService;

    /*
    // agrego uno
    @PostMapping
    public Brand addBrand(@RequestBody Brand brand) {
        return brandService.add(brand);
    }

    // traigo todos
    @GetMapping
    public List<Brand> getAll() {
        return brandService.getAll();
    }

    // traigo uno
    @GetMapping("/{id}")
    public Brand getById(@PathVariable Integer id) {
        return brandService.getById(id);
    }

    // actualizo
    @PutMapping("/{id}")
    public Brand replaceBrand(@PathVariable Integer id, @RequestBody Brand brand) {
        return brandService.update(id, brand);
    }

    // elimino uno
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        brandService.deleteById(id);
    }*/

}