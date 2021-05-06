package com.tp.udde.controller;

import com.tp.udde.domain.Model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tp.udde.service.ModelService;

@RestController
@RequestMapping("/model")
public class ModelController {

    @Autowired
    ModelService modelService;

    // agrego uno
    @PostMapping
    public Model addModel(@RequestBody Model model) {
        return modelService.add(model);
    }

    // traigo todos
    @GetMapping
    public List<Model> getAll() {
        return modelService.getAll();
    }

    // traigo uno
    @GetMapping("/{id}")
    public Model getById(@PathVariable Integer id) {
        return modelService.getById(id);
    }

    // actualizo
    @PutMapping("/{id}")
    public Model replaceModel(@PathVariable Integer id, @RequestBody Model model) {
        return modelService.update(id, model);
    }

    // elimino uno
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        modelService.deleteById(id);
    }

}