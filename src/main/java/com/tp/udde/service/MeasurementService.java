package com.tp.udde.service;

import com.tp.udde.domain.Invoice;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.tp.udde.domain.Measurement;
import com.tp.udde.repository.MeasurementRepository;

@Service
public class MeasurementService {

    @Autowired
    private MeasurementRepository measurementRepository;

    public List<Measurement> getAll() {
        List<Measurement> measurements = measurementRepository.findAll();
        return measurements;
    }

    public void deleteById(Integer id) {
        measurementRepository.delete(getById(id));
    }

    public Measurement add(Measurement measurement) {
        return measurementRepository.save(measurement);
    }

    public Measurement getById(Integer id) {
        return measurementRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public Measurement update(Integer id, Measurement measurement) {
        Optional<Measurement> measurementFind = measurementRepository.findById(id);
        if (measurementFind.isPresent()) {
            measurement.setId(id);
            return measurementRepository.save(measurement);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    // lab.4
    public Map<Float, Float> getConsumption(Integer user_id, String firstDate, String secondDate) {
        return measurementRepository.getConsumptionForDate(user_id,firstDate,secondDate);
    }

    // lab.5
    public List<Measurement> getMeasurementByDates(Integer userId, String startDate, String endDate){
        return measurementRepository.getMeasurementForDate(userId,startDate,endDate);
    }


}