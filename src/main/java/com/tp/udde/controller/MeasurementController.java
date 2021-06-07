package com.tp.udde.controller;


import com.tp.udde.domain.Measurement;
import com.tp.udde.domain.dto.MeasurementDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import com.tp.udde.service.MeasurementService;

@Slf4j
@Controller
public class MeasurementController {

    @Autowired
    MeasurementService measurementService;


    public Measurement addMeasurement(MeasurementDto measurementDto) {
        Measurement measurement = new Measurement();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dataTime = measurementDto.getDate().replaceAll("T"," ");
        Date date = null;
        try {
            date = dt.parse(dataTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        measurement.setDate(date);
        measurement.setKwh(measurementDto.getValue());
        return measurementService.add(measurement);
    }
    /*
    // agrego uno
    @PostMapping
    public Measurement addMeasurement(@RequestBody Measurement measurement) {
        return measurementService.add(measurement);
    }

    // traigo todos
    @GetMapping
    public List<Measurement> getAll() {
        return measurementService.getAll();
    }

    // traigo uno
    @GetMapping("/{id}")
    public Measurement getById(@PathVariable Integer id) {
        return measurementService.getById(id);
    }

    // actualizo
    @PutMapping("/{id}")
    public Measurement replaceMeasurement(@PathVariable Integer id, @RequestBody Measurement measurement) {
        return measurementService.update(id, measurement);
    }

    // elimino uno
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        measurementService.deleteById(id);
    }


    // lab.4 traigo consumo por rango por fechas (kwh y dinero en ese periodo)
    @GetMapping("/data/{id}/{firstDate}/{secondDate}")
        public Map<Float,Float> getConsumption(@PathVariable Integer user_id,
                @PathVariable(value = "firstDate", required = true) @DateTimeFormat(pattern = "YYYY-MM-DD") String firstDate,
                @PathVariable(value = "secondDate", required = true) @DateTimeFormat(pattern = "YYYY-MM-DD") String secondDate){
            return measurementService.getConsumption(user_id,firstDate,secondDate);
    }

    // lab.5 traigo las mediciones de entre fechas
    @GetMapping("/data/{id}/{firstDate}/{secondDate}")
    public List<Measurement> getMeasurementBetweenDates(@PathVariable Integer user_id,
                                                        @PathVariable(value = "firstDate", required = true) @DateTimeFormat(pattern = "YYYY-MM-DD") String firstDate,
                                                        @PathVariable(value = "secondDate", required = true) @DateTimeFormat(pattern = "YYYY-MM-DD") String secondDate){
        return measurementService.getMeasurementByDates(user_id,firstDate,secondDate);
    }
  */

}