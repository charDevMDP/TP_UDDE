package com.tp.udde.controller.web;

import com.tp.udde.controller.MeasurementController;
import com.tp.udde.controller.MeterController;
import com.tp.udde.controller.MetersForMeasurementController;
import com.tp.udde.domain.Measurement;
import com.tp.udde.domain.Meter;
import com.tp.udde.domain.dto.MeasurementDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/")
public class BroadcastController {

    private final MeasurementController measurementController;
    private final MeterController meterController;
    private final MetersForMeasurementController metersForMeasurementController;

    @Autowired
    public BroadcastController(MeasurementController measurementController, MeterController meterController, MetersForMeasurementController metersForMeasurementController) {
        this.measurementController = measurementController;
        this.meterController = meterController;
        this.metersForMeasurementController = metersForMeasurementController;
    }

    @PostMapping(value = "measurements")
    public void addMeasurement(@RequestBody MeasurementDto measurementDto) {
        if (measurementDto != null) {
            System.out.printf(measurementDto.getSerialNumber());
            Meter meter = this.meterController.getByMeterNumberAndPass(measurementDto.getSerialNumber(), measurementDto.getPassword());
            if (meter != null) {
                Measurement measurement = measurementController.addMeasurement(measurementDto);
                if (measurement != null) {
                    this.metersForMeasurementController.addMeterForMeasurement(meter, measurement);
                }
            }
        }
    }
}
