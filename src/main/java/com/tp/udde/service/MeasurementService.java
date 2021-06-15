package com.tp.udde.service;

import com.tp.udde.projections.Consumption;
import com.tp.udde.projections.UserMeasurementConsumption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.tp.udde.domain.Measurement;
import com.tp.udde.repository.MeasurementRepository;

@Service
public class MeasurementService {

    @Autowired
    private MeasurementRepository measurementRepository;

    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

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

    /*
    public Measurement update(Integer id, Measurement measurement) {
        Optional<Measurement> measurementFind = measurementRepository.findById(id);
        if (measurementFind.isPresent()) {
            measurement.setId(id);
            return measurementRepository.save(measurement);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

     */

    // lab.4
    public Consumption getConsumption(Integer meter_id, LocalDate firstDate, LocalDate secondDate) {
        return measurementRepository.getConsumptionForDate(meter_id,firstDate,secondDate);
    }

    // lab.5
    public Page<Measurement> getMeasurementByDates(Pageable pageable, Integer meterId, LocalDate startDate, LocalDate endDate){
        return measurementRepository.getMeasurementForDate(pageable, meterId,startDate,endDate);
    }

    // lab. Backoffice6
    public Page<Measurement> getMeasurementForDateForAddress(Pageable pageable, Integer idAddress, LocalDate startDate, LocalDate endDate){
        return measurementRepository.getMeasurementForDateForAddress(pageable, idAddress,startDate,endDate);
    }

    // lab. Backoffice5
    public Page<UserMeasurementConsumption> getUserForDateForConsumption(Pageable pageable, LocalDate startDate, LocalDate endDate){
        return measurementRepository.getUserForDateForConsumption(pageable, startDate,endDate);
    }


}