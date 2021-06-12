package com.tp.udde.controller.web;

import com.tp.udde.controller.*;
import com.tp.udde.domain.*;
import com.tp.udde.exception.NonExistentException;
import com.tp.udde.projections.InvoiceOwedAddressClient;
import com.tp.udde.projections.UserMeasurementConsumption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

import static com.tp.udde.utils.ResponsePage.response;

@RestController
@RequestMapping(value = "/backoffice")
public class BackOfficeController {

    private final UserController userController ;
    private final RateController rateController;
    private final AddressController addressController;
    private final MeterController meterController;
    private final InvoiceController invoiceController;
    private final MeasurementController measurementController;

    @Autowired
    public BackOfficeController(UserController userController, RateController rateController, AddressController addressController, MeterController meterController, InvoiceController invoiceController, MeasurementController measurementController) {
        this.userController = userController;
        this.rateController = rateController;
        this.addressController = addressController;
        this.meterController = meterController;
        this.invoiceController = invoiceController;
        this.measurementController = measurementController;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsers(Pageable pageable) {
        Page<User> users = this.userController.getUsers(pageable);
        return response(users);
    }

    //Rates**
    @GetMapping(value = "/rates")
    public ResponseEntity<List<Rate>> getRates(Pageable pageable){
        Page<Rate> rate = this.rateController.getRates(pageable);
        return response(rate);}

    @PostMapping(value = "/rate")//2) Alta de tarifas.
    public Rate addRate(@RequestBody Rate rate) {return this.rateController.addRate(rate);}

    @DeleteMapping(value = "/rate/{id}")//2) Baja de tarifas.
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws NonExistentException {
        this.rateController.deleteByIdRate(id);
        return ResponseEntity.accepted().build();
    }

    @PutMapping(value = "/rate/{id}")//2) Modificación de tarifas.
    public Rate replaceRate(@PathVariable Integer id, @RequestBody Rate rate) {return this.rateController.replaceRate(id, rate);}
    //**

    //Address**
    @GetMapping(value = "address")
    public ResponseEntity<List<Address>> getAll(Pageable pageable){
        Page<Address> address = this.addressController.getAll(pageable);
        return response(address); }

    @PostMapping(value = "address")
    public Address addAddress(@RequestBody Address address) {
        return addressController.addAddress(address);
    }

    @GetMapping("address/{id}")
    public  ResponseEntity<Address> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(addressController.getById(id));
    }

    @PutMapping("address/{id}")
    public Address replaceAddress(@PathVariable Integer id, @RequestBody Address address) {
        return addressController.updateAddressById(id, address);
    }

    @DeleteMapping("address/{id}")
    public ResponseEntity<String> deleteByIdAddress(@PathVariable Integer id) throws NonExistentException {
        this.addressController.deleteByIdAddress(id);
        return ResponseEntity.accepted().build();
    }
    //**

    //Meter**
    @GetMapping(value = "meter")
    public ResponseEntity<List<Meter>> getAllMeter(Pageable pageable) {
        Page<Meter> meters = this.meterController.getAllMeter(pageable);
        return response(meters);
    }

    @GetMapping(value ="meter/{id}")
    public Meter getByIdMeter(@PathVariable Integer id) {
        return meterController.getByIdMeter(id);
    }

    @PostMapping(value ="meter")
    public Meter addMeasurer(@RequestBody Meter meters) {
        return meterController.addMeasurer(meters);
    }

    @PutMapping(value ="meter/{id}")
    public Meter replaceMeter(@PathVariable Integer id, @RequestBody Meter meters) {
        return meterController.replaceMeter(id, meters);
    }

    @DeleteMapping(value ="meter/{id}")
    public void deleteByIdMeter(@PathVariable Integer id) {
        meterController.deleteByIdMeter(id);
    }
    //**

    //MeterAndAddress**
    @PostMapping(value ="meter/address")
    public Meter addMeasurerAddress(@RequestBody Meter meters) {
        return meterController.addMeasurer(meters);
    }
    //**


    // lab.4 traigo las facturas adeudadas por el cliente y el domicilio corespondiente.
    @GetMapping(value ="/client/invoices/{id}/owed")
    public ResponseEntity<List<InvoiceOwedAddressClient>> getInvoicesOwed(@PathVariable Integer id){
        List<InvoiceOwedAddressClient> invoiceOwedAddressClients =  this.invoiceController.getInvoicesOwedClient(id);
        if(invoiceOwedAddressClients!=null){
            return ResponseEntity.ok(invoiceOwedAddressClients);
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // lab.6 traigo las mediciones de entre fechas
    @GetMapping(value ="/measurements/address/{id}")
    public ResponseEntity<List<Measurement>> getMeasurementForDateForAddress(
            @PathVariable Integer id,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate firstDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate secondDate){
        List<Measurement> measurements =  this.measurementController.getMeasurementForDateForAddress(id,firstDate,secondDate);
        if(measurements!=null){
            return ResponseEntity.ok(measurements);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 5) Consulta 10 clientes más consumidores en un rango de fechas.
    @GetMapping(value ="/measurements/consumptions/")
    public ResponseEntity<List<UserMeasurementConsumption>> getUserForDateForConsumption(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate firstDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate secondDate){
        List<UserMeasurementConsumption> userMeasurementConsumptions =  this.measurementController.getUserForDateForConsumption(firstDate,secondDate);
        if(userMeasurementConsumptions!=null){
            return ResponseEntity.ok(userMeasurementConsumptions);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }






}
