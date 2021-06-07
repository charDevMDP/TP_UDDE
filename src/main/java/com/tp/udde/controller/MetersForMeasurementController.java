package com.tp.udde.controller;


import com.tp.udde.domain.Measurement;
import com.tp.udde.domain.Meter;
import com.tp.udde.domain.MetersForMeasurement;
import com.tp.udde.service.MetersForMeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MetersForMeasurementController {

    @Autowired
    private MetersForMeasurementService metersForMeasurementService;

    public MetersForMeasurement addMeterForMeasurement(Meter meter, Measurement measurement) {
            MetersForMeasurement  metersForMeasurement = new MetersForMeasurement();
            metersForMeasurement.setMeasurement(measurement);
            metersForMeasurement.setMeters(meter);
            return this.metersForMeasurementService.add(metersForMeasurement);
    }
}
