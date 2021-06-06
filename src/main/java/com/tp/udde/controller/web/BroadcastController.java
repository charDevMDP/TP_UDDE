package com.tp.udde.controller.web;

import com.tp.udde.controller.MeasurementController;
import com.tp.udde.controller.MeterController;
import com.tp.udde.controller.RateController;
import com.tp.udde.domain.Measurement;
import com.tp.udde.domain.dto.MeasurementDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/")
public class BroadcastController {

    private final MeasurementController measurementController;
    private final MeterController meterController;
    private final RateController rateController;

    public BroadcastController(MeasurementController measurementController, MeterController meterController, RateController rateController) {
        this.measurementController = measurementController;
        this.meterController = meterController;
        this.rateController = rateController;
    }

    @PostMapping(value = "measurements")
    public Measurement addRate(@RequestBody MeasurementDto measurementDto) {
        System.out.printf(measurementDto.getDate());
        if (!(measurementDto.equals(null))) {
            return measurementController.addMeasurement(measurementDto);
        }
        else
        {
            return (Measurement) ResponseEntity.notFound();
        }
    }

}
