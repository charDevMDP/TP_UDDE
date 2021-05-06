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
import com.tp.udde.domain.Measurer;
import com.tp.udde.repository.MeasurerRepository;

@Service
public class MeasurerService {

    @Autowired
    private MeasurerRepository measurerRepository;

    public List<Measurer> getAll() {
        List<Measurer> measurers = measurerRepository.findAll();
        return measurers;
    }

    public void deleteById(Integer id) {
        measurerRepository.delete(getById(id));
    }

    public Measurer add(Measurer measurer) {
        return measurerRepository.save(measurer);
    }

    public Measurer getById(Integer id) {
        return measurerRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public Measurer update(Integer id, Measurer measurer) {
        Optional<Measurer> measurerFind = measurerRepository.findById(id);
        if (measurerFind.isPresent()) {
            measurer.setId(id);
            return measurerRepository.save(measurer);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}