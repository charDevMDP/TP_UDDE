package com.tp.udde.controller;

import com.tp.udde.domain.Meter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import com.tp.udde.service.MetersService;

import java.util.List;

@Controller
public class MeterController {

    @Autowired
    private MetersService metersService;

    public MeterController(MetersService meterService) {
        this.metersService = meterService;
    }

    // traigo todos
    public Page<Meter> getAllMeter(Pageable pageable) {
        return metersService.getAll(pageable);
    }

    // traigo uno
    public Meter getByIdMeter(Integer id) {
        return metersService.getById(id);
    }

    // agrego uno
    public Meter addMeter(Meter meters) {
        return metersService.add(meters);
    }

    // actualizo
    public Meter replaceMeter(Integer id, Meter meters) {
        return metersService.update(id, meters);
    }

    // elimino uno
    public void deleteByIdMeter(Integer id) {
        metersService.deleteById(id);
    }

    //Taigo el meter por Number y Pass
    public Meter getByMeterNumberAndPass(String number, String password) {
        return metersService.getByMeterNumberAndPass(number,password);
    }


}