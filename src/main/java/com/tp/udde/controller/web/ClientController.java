package com.tp.udde.controller.web;

import com.tp.udde.controller.InvoiceController;
import com.tp.udde.controller.MeasurementController;
import com.tp.udde.controller.UserController;
import com.tp.udde.domain.Invoice;
import com.tp.udde.domain.Measurement;
import com.tp.udde.domain.User;
import com.tp.udde.projections.Consumption;
import com.tp.udde.projections.MeterUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/client")
public class ClientController {

    private final UserController userController ;
    private final InvoiceController invoiceController;
    private final MeasurementController measurementController;

    @Autowired
    public ClientController(UserController userController, InvoiceController invoiceController, MeasurementController measurementController) {
        this.userController = userController;
        this.invoiceController = invoiceController;
        this.measurementController = measurementController;
    }

    // traigo el medidor de un usuario
    @GetMapping("/meterUser")
    public ResponseEntity<MeterUser> meterUser(@RequestParam Integer idUser) {
        MeterUser meterUser = userController.meterofuser(idUser);
        if(meterUser!=null){
            return ResponseEntity.ok(meterUser);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // lab.2 traigo las facturas entre fechas
    @PreAuthorize(value= "hasAuthority('BACKOFFICE') or authentication.principal.id.equals(#id)")
    @GetMapping("/invoices/{id}")
    public ResponseEntity<List<Invoice>> getInvoiceBetweenDates(
            @PathVariable Integer id,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate firstDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate secondDate
    ){

        List<Invoice> invoices =  invoiceController.getInvoiceBetweenDates(id, firstDate,secondDate);
        if(invoices!=null){
            return ResponseEntity.ok(invoices);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }


    // lab.3 traigo las facturas adeudadas
    @PreAuthorize(value= "hasAuthority('BACKOFFICE') or authentication.principal.id.equals(#id)")
    @GetMapping("/invoices/{id}/owed")
    public ResponseEntity<List<Invoice>> getInvoicesOwed(@PathVariable Integer id){
        List<Invoice> invoices =  invoiceController.getInvoicesOwed(id);
        if(invoices!=null){
            return ResponseEntity.ok(invoices);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    // lab.4 traigo consumo por rango por fechas (kwh y dinero en ese periodo)
    @PreAuthorize(value= "hasAuthority('BACKOFFICE') or authentication.principal.id.equals(#id)")
    @GetMapping("/consumption/{id}")
    public ResponseEntity<Consumption> getConsumption(
            @PathVariable Integer id,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date firstDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date secondDate
    ){
        MeterUser meterUser = userController.meterofuser(id);
        Consumption consumption = measurementController.getConsumption(meterUser.getNumberMeter(),firstDate,secondDate);
        if(consumption!=null){
            return ResponseEntity.ok(consumption);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // lab.5 traigo las mediciones de entre fechas
    @PreAuthorize(value= "hasAuthority('BACKOFFICE') or authentication.principal.id.equals(#id)")
    @GetMapping("/measurements/{id}")
    public ResponseEntity<List<Measurement>> getMeasurementBetweenDates(
            @PathVariable Integer id,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate firstDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate secondDate
    ){
        MeterUser meterUser = userController.meterofuser(id);
        log.info("medidor");
        log.info(meterUser.getNumberMeter().toString());
        List<Measurement> measurements =  measurementController.getMeasurementBetweenDates(meterUser.getNumberMeter(),firstDate,secondDate);
        if(measurements!=null){
            return ResponseEntity.ok(measurements);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /* TRAER LISTA DE USUARIO
    //@PreAuthorize(value = "hasAuthority('CLIENT')")
    @GetMapping(value = "/users")
    public List<User> getUsers() {
        return this.userController.getUsers();
    }
     */

}
