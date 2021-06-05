package com.tp.udde.controller;

import com.tp.udde.domain.Address;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.tp.udde.service.AddressService;

@Controller
public class AddressController {

    @Autowired
    AddressService addressService;

    // agrego uno
    @PostMapping
    public Address addAddress(@RequestBody Address address) {
        return addressService.add(address);
    }

    // traigo todos
    public List<Address> getAll() { return addressService.getAll();}

    // traigo uno
    public Address getById(@PathVariable Integer id) {
        return addressService.getById(id);
    }

    // actualizo
    public Address updateAddressById(@PathVariable Integer id, @RequestBody Address address) {
        return addressService.update(id, address);
    }

    // elimino uno
    @DeleteMapping("/{id}")
    public void deleteByIdAddress(@PathVariable Integer id) {
        addressService.deleteById(id);
    }

}