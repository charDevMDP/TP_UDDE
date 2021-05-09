package com.tp.udde.controller;

import com.tp.udde.domain.Invoice;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tp.udde.service.InvoiceService;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

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

}