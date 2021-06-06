package com.tp.udde.service;

import com.tp.udde.domain.MetersForMeasurement;
import com.tp.udde.repository.MetersForMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetersForMeasurementService {

    @Autowired
    private MetersForMeasurementRepository metersForMeasurementRepository;


    public MetersForMeasurement add(MetersForMeasurement measurement) {
        return this.metersForMeasurementRepository.save(measurement);
    }
}
