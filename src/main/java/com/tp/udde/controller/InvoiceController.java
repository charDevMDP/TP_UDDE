package com.tp.udde.controller;

import com.tp.udde.domain.Invoice;
import com.tp.udde.projections.InvoiceOwedAddressClient;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.tp.udde.service.InvoiceService;

@Controller
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;
    /*
    // agrego uno
    @PostMapping
    public Invoice addInvoice(@RequestBody Invoice invoice) {
        return invoiceService.add(invoice);
    }

    // traigo todos
    @GetMapping
    public List<Invoice> getAll() {
        return invoiceService.getAll();
    }

    // traigo uno
    @GetMapping("/{id}")
    public Invoice getById(@PathVariable Integer id) {
        return invoiceService.getById(id);
    }

    // actualizo
    @PutMapping("/{id}")
    public Invoice replaceInvoice(@PathVariable Integer id, @RequestBody Invoice invoice) {
        return invoiceService.update(id, invoice);
    }

    // elimino uno
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        invoiceService.deleteById(id);
    }
*/

    // lab.2 traigo las facturas de entre fechas
    public Page<Invoice> getInvoiceBetweenDates(Pageable pageable, Integer meterId, LocalDate initialDate, LocalDate endDate){
        return invoiceService.getInvoiceBetweenDates(pageable,meterId,initialDate,endDate);
    }


    // lab.3 traigo las facturas adeudadas
    public Page<Invoice> getInvoicesOwed(Pageable pageable, Integer userId){
        return  invoiceService.getInvoicesOwed(pageable,userId);
    }

    /* Backoffice **/
    // lab.4 traigo las facturas adeudadas por el cliente y el domicilio corespondiente.
    public Page<InvoiceOwedAddressClient> getInvoicesOwedClient(Pageable pageable,Integer userId){
        return  invoiceService.getInvoicesOwedClient(pageable, userId);
    }


}