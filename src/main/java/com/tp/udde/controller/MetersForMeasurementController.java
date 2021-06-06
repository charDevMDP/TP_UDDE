package com.tp.udde.controller;


import com.tp.udde.domain.Measurement;
import com.tp.udde.domain.Meter;
import com.tp.udde.domain.MetersForMeasurement;
import com.tp.udde.service.MetersForMeasurementService;
import org.springframework.stereotype.Controller;

@Controller
public class MetersForMeasurementController {

    private MetersForMeasurementService metersForMeasurementService;

    public MetersForMeasurement addMeterForMeasurement(Meter meter, Measurement measurement) {
        if(!meter.equals(null) && !measurement.equals(null)){
            MetersForMeasurement  metersForMeasurement = new MetersForMeasurement();
            metersForMeasurement.setMeasurement(measurement);
            metersForMeasurement.setMeters(meter);
            return this.metersForMeasurementService.add(metersForMeasurement);
        }
        return null;
    }
}
