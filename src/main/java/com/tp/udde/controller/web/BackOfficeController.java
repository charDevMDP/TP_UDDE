package com.tp.udde.controller.web;


import com.tp.udde.controller.AddressController;
import com.tp.udde.controller.MeterController;
import com.tp.udde.controller.RateController;
import com.tp.udde.controller.UserController;
import com.tp.udde.domain.Address;
import com.tp.udde.domain.Meter;
import com.tp.udde.domain.Rate;
import com.tp.udde.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/backoffice")
public class BackOfficeController {

    private final UserController userController ;
    private final RateController rateController;
    private final AddressController addressController;
    private final MeterController meterController;

    @Autowired
    public BackOfficeController(UserController userController, RateController rateController, AddressController addressController, MeterController meterController) {
        this.userController = userController;
        this.rateController = rateController;
        this.addressController = addressController;
        this.meterController = meterController;
    }

    @GetMapping(value = "/users")
    public List<User> getUsers() {
        return this.userController.getUsers();
    }


    //Rates**
    @GetMapping(value = "/rates")
    public List<Rate> getRates(){ return this.rateController.getRates();}

    @PostMapping(value = "/rate")//2) Alta de tarifas.
    public Rate addRate(@RequestBody Rate rate) {return this.rateController.addRate(rate);}

    @DeleteMapping(value = "/rate/{id}")//2) Baja de tarifas.
    public void deleteById(@PathVariable Integer id) {this.rateController.deleteByIdRate(id);}

    @PutMapping(value = "/rate/{id}")//2) Modificación de tarifas.
    public Rate replaceRate(@PathVariable Integer id, @RequestBody Rate rate) {return this.rateController.replaceRate(id, rate);}
    //**

    //Address**
    @GetMapping(value = "address")
    public List<Address> getAll() { return this.addressController.getAll(); }

    @PostMapping(value = "address")
    public Address addAddress(@RequestBody Address address) {
        return addressController.addAddress(address);
    }

    @GetMapping("address/{id}")
    public Address getById(@PathVariable Integer id) {
        return addressController.getById(id);
    }

    @PutMapping("address/{id}")
    public Address replaceAddress(@PathVariable Integer id, @RequestBody Address address) {
        return addressController.updateAddressById(id, address);
    }

    @DeleteMapping("address/{id}")
    public void deleteByIdAddress(@PathVariable Integer id) {
        addressController.deleteByIdAddress(id);
    }
    //**

    //Meter**
    @GetMapping(value = "meter")
    public List<Meter> getAllMeter() {
        return meterController.getAllMeter();
    }

    @GetMapping("meter/{id}")
    public Meter getByIdMeter(@PathVariable Integer id) {
        return meterController.getByIdMeter(id);
    }

    @PostMapping("meter")
    public Meter addMeasurer(@RequestBody Meter meters) {
        return meterController.addMeasurer(meters);
    }

    @PutMapping("meter/{id}")
    public Meter replaceMeter(@PathVariable Integer id, @RequestBody Meter meters) {
        return meterController.replaceMeter(id, meters);
    }

    @DeleteMapping("meter/{id}")
    public void deleteByIdMeter(@PathVariable Integer id) {
        meterController.deleteByIdMeter(id);
    }
    //**

    //MeterAndAddress**
    @PostMapping("meter/address")
    public Meter addMeasurerAddress(@RequestBody Meter meters) {
        return meterController.addMeasurer(meters);
    }
    //**





}
