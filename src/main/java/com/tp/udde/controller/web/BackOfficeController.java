package com.tp.udde.controller.web;


import com.tp.udde.controller.AddressController;
import com.tp.udde.controller.RateController;
import com.tp.udde.controller.UserController;
import com.tp.udde.domain.Address;
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

    @Autowired
    public BackOfficeController(UserController userController, RateController rateController, AddressController addressController) {
        this.userController = userController;
        this.rateController = rateController;
        this.addressController = addressController;
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
    
}
