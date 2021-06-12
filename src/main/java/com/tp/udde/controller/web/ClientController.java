package com.tp.udde.controller.web;

import com.tp.udde.controller.InvoiceController;
import com.tp.udde.controller.MeasurementController;
import com.tp.udde.controller.UserController;
import com.tp.udde.domain.Invoice;
import com.tp.udde.domain.Measurement;
import com.tp.udde.domain.User;
import com.tp.udde.exception.ClientNotExists;
import com.tp.udde.projections.Consumption;
import com.tp.udde.projections.MeterUser;
import com.tp.udde.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static com.tp.udde.utils.ResponsePage.response;


@RestController
@RequestMapping(value = "/client")
@Slf4j
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

    //traigo uno
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Integer id) throws ClientNotExists{
         User user = userController.getById(id);
            return ResponseEntity.ok(user);
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
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate secondDate,
            Pageable pageable
    ) throws ClientNotExists {
        User user =  userController.getById(id); // veo primero si el usuario existe
        if(user != null){
            Page<Invoice> invoices =  invoiceController.getInvoiceBetweenDates(pageable, id, firstDate,secondDate);
            if(invoices!=null){
                if(invoices.isEmpty()){  return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); }
                return response(invoices);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    // lab.3 traigo las facturas adeudadas
    @PreAuthorize(value= "hasAuthority('BACKOFFICE') or authentication.principal.id.equals(#id)")
    @GetMapping("/invoices/{id}/owed")
    public ResponseEntity<List<Invoice>> getInvoicesOwed( @PathVariable Integer id,Pageable pageable) throws ClientNotExists {
        User user =  userController.getById(id);
        if(user != null){
            Page<Invoice> invoices =  invoiceController.getInvoicesOwed(pageable,id);
            if(invoices!=null){
                if(invoices.isEmpty()){  return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); }
                return response(invoices);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    // lab.4 traigo consumo por rango por fechas (kwh y dinero en ese periodo)
    @PreAuthorize(value= "hasAuthority('BACKOFFICE') or authentication.principal.id.equals(#id)")
    @GetMapping("/consumption/{id}")
    public ResponseEntity<Consumption> getConsumption(
            @PathVariable Integer id,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate firstDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate secondDate
    ) throws ClientNotExists {
        User user =  userController.getById(id);
        if(user != null) {
            MeterUser meterUser = userController.meterofuser(id);
            Consumption consumption = measurementController.getConsumption(meterUser.getNumberMeter(), firstDate, secondDate);
            if (consumption.getPriceTotal() != null || consumption.getTotalKwh() != null) {
                return ResponseEntity.ok(consumption);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
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
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate secondDate,
            Pageable pageable
    ) throws ClientNotExists {
        User user =  userController.getById(id);
        if(user != null) {
            MeterUser meterUser = userController.meterofuser(id);
            Page<Measurement> measurements = measurementController.getMeasurementBetweenDates(pageable, meterUser.getNumberMeter(), firstDate, secondDate);
            if (measurements != null) {
                return response(measurements);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
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
