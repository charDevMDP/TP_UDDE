package com.tp.udde.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.tp.udde.domain.Model;
import com.tp.udde.repository.ModelRepository;

@Service
public class ModelService {


    private ModelRepository modelRepository;

    @Autowired
    public ModelService(ModelRepository modelRepository) {
       this.modelRepository = modelRepository;
    }

    public List<Model> getAll() {
        List<Model> models = modelRepository.findAll();
        return models;
    }

    public void deleteById(Integer id) {
        modelRepository.delete(getById(id));
    }

    public Model add(Model model) {
        return modelRepository.save(model);
    }

    public Model getById(Integer id) {
        return modelRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public Model update(Integer id, Model model) {
        Optional<Model> modelFind = modelRepository.findById(id);
        if (modelFind.isPresent()) {
            model.setId(id);
            return modelRepository.save(model);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}